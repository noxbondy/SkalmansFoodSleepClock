package se.lexicon.skalmansfoodsleepclock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import se.lexicon.skalmansfoodsleepclock.service.ChatClientService;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class OpenAIController {

    private final ChatClientService chatClientService;


    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody ChatRequest request) {
        if (request.getQuery() == null || request.getQuery().isBlank()) {
            return ResponseEntity.badRequest().body("Query cannot be empty");
        }
        try {
            String response = chatClientService.chatWithMemory(request.getQuery());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Error processing request: " + e.getMessage());
        }
    }

    @PostMapping("/chat/stream")
    public Flux<String> chatStream(@RequestBody ChatRequest request) {
        return chatClientService.chatWithMemoryRealTime(request.getQuery());
    }

    @GetMapping("/chat/history")
    public ResponseEntity<List<?>> getMessages() {
        return ResponseEntity.ok(chatClientService.getMessages());
    }

    @DeleteMapping("/chat/clear")
    public ResponseEntity<String> clearChat() {
        chatClientService.clearChatMemory();
        return ResponseEntity.ok("✅ Chat memory cleared");
    }

    @GetMapping("/ping")
    public String ping() {
        return "Flight Booking Assistant is up and running 🚀";
    }

    public static class ChatRequest {
        private String query;

        public ChatRequest() {}

        public ChatRequest(String query) {
            this.query = query;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }
    }



}
