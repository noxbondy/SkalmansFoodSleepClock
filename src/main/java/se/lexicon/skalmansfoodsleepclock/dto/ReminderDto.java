package se.lexicon.skalmansfoodsleepclock.dto;

import se.lexicon.skalmansfoodsleepclock.Entity.User;
import se.lexicon.skalmansfoodsleepclock.Entity.reminder.Recurrence;
import se.lexicon.skalmansfoodsleepclock.Entity.reminder.Reminder;
import se.lexicon.skalmansfoodsleepclock.Entity.reminder.ReminderType;

import java.time.LocalDateTime;

public record ReminderDto(
        Long id,
        String personalNumber, // link to User
        ReminderType type,
        String title,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Recurrence recurrence,
        String soundTheme,
        String visualTheme,
        boolean active
) {
    // Convert DTO -> Entity
    public Reminder toEntity(User user) {
        Reminder reminder = new Reminder();
        reminder.setType(type);
        reminder.setTitle(title);
        reminder.setDescription(description);
        reminder.setStartTime(startTime);
        reminder.setEndTime(endTime);
        reminder.setRecurrence(recurrence);
        reminder.setSoundTheme(soundTheme);
        reminder.setVisualTheme(visualTheme);
        reminder.setActive(active);
        reminder.setUser(user);
        return reminder;
    }

    // Convert Entity -> DTO
    public static ReminderDto fromEntity(Reminder reminder) {
        return new ReminderDto(
                reminder.getId(),
                reminder.getUser() != null ? reminder.getUser().getPersonalNumber() : null,
                reminder.getType(),
                reminder.getTitle(),
                reminder.getDescription(),
                reminder.getStartTime(),
                reminder.getEndTime(),
                reminder.getRecurrence(),
                reminder.getSoundTheme(),
                reminder.getVisualTheme(),
                reminder.isActive()
        );
    }
}
