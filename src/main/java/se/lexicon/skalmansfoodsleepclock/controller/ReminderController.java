package se.lexicon.skalmansfoodsleepclock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import se.lexicon.skalmansfoodsleepclock.Entity.Role;
import se.lexicon.skalmansfoodsleepclock.Entity.User;
import se.lexicon.skalmansfoodsleepclock.dto.ReminderDto;
import se.lexicon.skalmansfoodsleepclock.service.AuthService;
import se.lexicon.skalmansfoodsleepclock.service.ReminderService;

import java.util.List;
@CrossOrigin(origins = "https://clock-react-eight.vercel.app")
@RestController
@RequestMapping("/api/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;
    private final AuthService authService;



    @PostMapping("/{personalNumber}")
    public ResponseEntity<ReminderDto> createReminder(
            @PathVariable String personalNumber,
            @RequestBody ReminderDto reminderDto) {

        ReminderDto created = reminderService.createReminder(personalNumber, reminderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ✅ Get all reminders for a user
    @GetMapping("/patient/{personalNumber}")
    public ResponseEntity<List<ReminderDto>> getRemindersByUser(
            @PathVariable String personalNumber) {
        List<ReminderDto> reminders = reminderService.getRemindersByUser(personalNumber);
        return ResponseEntity.ok(reminders);
    }

    // ✅ Update reminder by ID
    @PutMapping("/{reminderId}")
    public ResponseEntity<ReminderDto> updateReminder(
            @PathVariable Long reminderId,
            @RequestBody ReminderDto reminderDto
    ) {
        ReminderDto updated = reminderService.updateReminder(reminderId, reminderDto);
        return ResponseEntity.ok(updated);
    }

    // ✅ Delete reminder by ID
    @DeleteMapping("/{reminderId}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Long reminderId) {
        reminderService.deleteReminder(reminderId);
        return ResponseEntity.noContent().build();
    }


}
