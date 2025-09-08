package se.lexicon.skalmansfoodsleepclock.service;

import se.lexicon.skalmansfoodsleepclock.dto.ReminderDto;

import java.util.List;

public interface ReminderService {

    // ✅ Create a new reminder for a user
    ReminderDto createReminder(String personalNumber, ReminderDto reminderDto);

    // ✅ Get all reminders for a user
    List<ReminderDto> getRemindersByUser(String personalNumber);

    // ✅ Update a reminder by its ID
    ReminderDto updateReminder(Long reminderId, ReminderDto reminderDto);

    // ✅ Delete a reminder by its ID
    void deleteReminder(Long reminderId);
}
