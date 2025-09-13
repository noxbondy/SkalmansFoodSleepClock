package se.lexicon.skalmansfoodsleepclock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import se.lexicon.skalmansfoodsleepclock.Entity.User;
import se.lexicon.skalmansfoodsleepclock.Entity.assigneeTask.AssigneeTask;
import se.lexicon.skalmansfoodsleepclock.dto.*;

@Component
@RequiredArgsConstructor
public class AppToolCalling {
    private final AuthService authService;
    private final MealService mealService;
    private final ReminderService reminderService;
    private final AssigneeTaskService assigneeTaskService;

    // ----------------- AuthService -----------------
    @Tool(name = "loginUser", description = "Logs in a user using email and password")
    public String loginUser(String email, String password) {
        try {
            UserDto user = authService.login(new LoginRequestDto(email, password));
            return "✅ Login successful for: " + user.personalNumber();
        } catch (Exception e) {
            return "❌ Login failed: " + e.getMessage();
        }
    }

    @Tool(name = "registerUser", description = "Registers a user with details and role")
    public String registerUser(RegisterRequestDto dto, String roleName) {
        try {
            User user = authService.register(dto, roleName);
            return "✅ User registered: " + user.getPersonalNumber();
        } catch (Exception e) {
            return "❌ Registration failed: " + e.getMessage();
        }
    }

    @Tool(name = "deleteUser", description = "Deletes a user by personal number")
    public String deleteUser(String personalNumber) {
        try {
            authService.deleteUser(personalNumber);
            return "✅ User deleted: " + personalNumber;
        } catch (Exception e) {
            return "❌ Delete failed: " + e.getMessage();
        }
    }

    // ----------------- MealService -----------------
    @Tool(name = "createMeal", description = "Creates a meal for a user")
    public String createMeal(String personalNumber, MealDto mealDto) {
        try {
            MealDto meal = mealService.createMeal(personalNumber, mealDto);
            return "✅ Meal created: " + meal.mealName() + " for user " + personalNumber;
        } catch (Exception e) {
            return "❌ Meal creation failed: " + e.getMessage();
        }
    }

    @Tool(name = "deleteMeal", description = "Deletes a meal by ID")
    public String deleteMeal(Long mealId) {
        try {
            mealService.deleteMeal(mealId);
            return "✅ Meal deleted with ID: " + mealId;
        } catch (Exception e) {
            return "❌ Meal deletion failed: " + e.getMessage();
        }
    }

    // ----------------- ReminderService -----------------
    @Tool(name = "createReminder", description = "Creates a reminder for a user")
    public String createReminder(String personalNumber, ReminderDto reminderDto) {
        try {
            ReminderDto reminder = reminderService.createReminder(personalNumber, reminderDto);
            return "✅ Reminder created: " + reminder.title() + " for user " + personalNumber;
        } catch (Exception e) {
            return "❌ Reminder creation failed: " + e.getMessage();
        }
    }

    @Tool(name = "deleteReminder", description = "Deletes a reminder by ID")
    public String deleteReminder(Long reminderId) {
        try {
            reminderService.deleteReminder(reminderId);
            return "✅ Reminder deleted with ID: " + reminderId;
        } catch (Exception e) {
            return "❌ Reminder deletion failed: " + e.getMessage();
        }
    }

    // ----------------- AssigneeTaskService -----------------
    @Tool(name = "createTask", description = "Creates a task for a user")
    public String createTask(AssigneeTask task, String personalNumber) {
        try {
            AssigneeTask created = assigneeTaskService.createTask(task, personalNumber);
            return "✅ Task created: " + created.getTitle() + " for user " + personalNumber;
        } catch (Exception e) {
            return "❌ Task creation failed: " + e.getMessage();
        }
    }

    @Tool(name = "completeTask", description = "Marks a task as complete")
    public String completeTask(Long taskId) {
        try {
            AssigneeTask task = assigneeTaskService.completeTask(taskId);
            return "✅ Task completed: " + task.getTitle();
        } catch (Exception e) {
            return "❌ Task completion failed: " + e.getMessage();
        }
    }

    @Tool(name = "deleteTask", description = "Deletes a task by ID")
    public String deleteTask(Long taskId) {
        try {
            assigneeTaskService.deleteTask(taskId);
            return "✅ Task deleted with ID: " + taskId;
        } catch (Exception e) {
            return "❌ Task deletion failed: " + e.getMessage();
        }
    }
}

