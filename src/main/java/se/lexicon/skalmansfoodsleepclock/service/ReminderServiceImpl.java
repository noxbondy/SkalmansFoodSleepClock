package se.lexicon.skalmansfoodsleepclock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.skalmansfoodsleepclock.Entity.User;
import se.lexicon.skalmansfoodsleepclock.Entity.reminder.Reminder;
import se.lexicon.skalmansfoodsleepclock.dto.ReminderDto;
import se.lexicon.skalmansfoodsleepclock.repository.ReminderRepository;
import se.lexicon.skalmansfoodsleepclock.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {

  private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ReminderDto createReminder(String personalNumber, ReminderDto reminderDto) {
        User user = userRepository.findByPersonalNumber(personalNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Reminder reminder = reminderDto.toEntity(user);
        reminderRepository.save(reminder);

        return ReminderDto.fromEntity(reminder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReminderDto> getRemindersByUser(String personalNumber) {
        List<Reminder> reminders = reminderRepository.findByUserPersonalNumber(personalNumber);
        return reminders.stream()
                .map(ReminderDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReminderDto updateReminder(Long reminderId, ReminderDto reminderDto) {
        Reminder reminder = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new RuntimeException("Reminder not found"));

        reminder.setType(reminderDto.type());
        reminder.setTitle(reminderDto.title());
        reminder.setDescription(reminderDto.description());
        reminder.setStartTime(reminderDto.startTime());
        reminder.setEndTime(reminderDto.endTime());
        reminder.setRecurrence(reminderDto.recurrence());
        reminder.setSoundTheme(reminderDto.soundTheme());
        reminder.setVisualTheme(reminderDto.visualTheme());
        reminder.setActive(reminderDto.active());

        reminderRepository.save(reminder);

        return ReminderDto.fromEntity(reminder);
    }

    @Override
    @Transactional
    public void deleteReminder(Long reminderId) {
        Reminder reminder = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new RuntimeException("Reminder not found"));
        reminderRepository.delete(reminder);
    }
}
