package se.lexicon.skalmansfoodsleepclock.service;


import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;











@Service
public class ChatClientServiceImpl implements ChatClientService {

    private final ChatClient chatClient;
    private final ChatMemory chatMemory;
    private final AppToolCalling appToolCalling;


    // We'll store all chat in a single "default" conversation
    private static final String DEFAULT_CONVERSATION_ID = "global-chat";

    private final String systemMessage = """
             You are a helpful Skalman user assistant. Your role is to help users:
              - Find available meal, reminder, and tasks
              - Find their meals
              - Create meals
              - Create reminders
              - Check their existing meals and reminders

            Always be polite and confirm actions with users before executing them.
            When showing meal and reminder information, include all relevant details.
            For this service, always confirm user details before proceeding.
            """;

    @Autowired
    public ChatClientServiceImpl(ChatMemory chatMemory,
                                 ChatClient.Builder chatClient,
                                 AppToolCalling appToolCalling) {
        this.chatMemory = chatMemory;
        this.appToolCalling = appToolCalling;
        this.chatClient = chatClient
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(this.chatMemory).build()
                )
                .build();
    }

    @Override
    public String chatWithMemory(String query) {
        ChatResponse chatResponse = this.chatClient
                .prompt()
                .user(query)
                .system(systemMessage)
                .tools(appToolCalling)
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, DEFAULT_CONVERSATION_ID))
                .options(OpenAiChatOptions.builder()
                        .temperature(0.2)
                        .maxTokens(1000)
                        .build())
                .call()
                .chatResponse();

        Generation result = chatResponse != null ? chatResponse.getResult() : null;
        return result != null ? result.getOutput().getText() : "No response received.";
    }

    @Override
    public Flux<String> chatWithMemoryRealTime(String query) {
        Flux<ChatResponse> chatResponse = this.chatClient
                .prompt()
                .user(query)
                .system(systemMessage)
                .tools(appToolCalling)
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, DEFAULT_CONVERSATION_ID))
                .options(OpenAiChatOptions.builder()
                        .temperature(0.2)
                        .maxTokens(1000)
                        .build())
                .stream()
                .chatResponse();

        return chatResponse
                .flatMapIterable(ChatResponse::getResults)
                .mapNotNull(result -> result.getOutput().getText());
    }

    @Override
    public List<Message> getMessages() {
        return chatMemory.get(DEFAULT_CONVERSATION_ID);
    }

    @Override
    public void clearChatMemory() {
        this.chatMemory.clear(DEFAULT_CONVERSATION_ID);
    }

    @Override
    public String chatMemory(String question) {
        return chatWithMemory(question);
    }

}
