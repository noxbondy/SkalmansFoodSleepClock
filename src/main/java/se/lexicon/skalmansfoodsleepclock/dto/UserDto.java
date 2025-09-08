package se.lexicon.skalmansfoodsleepclock.dto;

import se.lexicon.skalmansfoodsleepclock.Entity.Meal;
import se.lexicon.skalmansfoodsleepclock.Entity.Role;
import se.lexicon.skalmansfoodsleepclock.Entity.User;
import se.lexicon.skalmansfoodsleepclock.Entity.assigneeTask.AssigneeTask;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.Set;

public record UserDto(
        String personalNumber,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String gender,
        String phoneNumber,
        String address,
        String email,
        Role role,
        List<MealDto> meals,
        List<ReminderDto> reminders,
        List<AssigneeTaskDto> assigneeTasks
) {
    public static UserDto fromEntity(User user) {
        return new UserDto(
                user.getPersonalNumber(),
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth(),
                user.getGender(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getEmail(),
                user.getRole(),
                user.getMeals().stream().map(MealDto::fromEntity).toList(),
                user.getReminders().stream().map(ReminderDto::fromEntity).toList(),
                user.getTasks().stream().map(AssigneeTaskDto::fromEntity).toList()
        );
    }
}
