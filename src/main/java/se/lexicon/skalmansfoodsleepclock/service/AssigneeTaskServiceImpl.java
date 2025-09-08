package se.lexicon.skalmansfoodsleepclock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.skalmansfoodsleepclock.Entity.User;
import se.lexicon.skalmansfoodsleepclock.Entity.assigneeTask.AssigneeTask;
import se.lexicon.skalmansfoodsleepclock.dto.AssigneeTaskDto;
import se.lexicon.skalmansfoodsleepclock.dto.MealDto;
import se.lexicon.skalmansfoodsleepclock.dto.ReminderDto;
import se.lexicon.skalmansfoodsleepclock.dto.UserDto;
import se.lexicon.skalmansfoodsleepclock.repository.AssigneeTaskRepository;
import se.lexicon.skalmansfoodsleepclock.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssigneeTaskServiceImpl implements AssigneeTaskService {

private final AssigneeTaskRepository taskRepository;
private final UserRepository userRepository;



    @Override
    @Transactional(readOnly = true)
    public List<AssigneeTask> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getPatientWithTasks(String personalNumber) {
        User user = userRepository.findByPersonalNumber(personalNumber)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        List<AssigneeTask> tasks = taskRepository.findByUserPersonalNumber(personalNumber);

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
                tasks.stream().map(AssigneeTaskDto::fromEntity).toList()
        );
    }

    @Override
    @Transactional
    public AssigneeTask completeTask(Long taskId) {
        AssigneeTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        task.setCompleted(true);
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public AssigneeTask createTask(AssigneeTask task, String userPersonalNumber) {
        User user = userRepository.findByPersonalNumber(userPersonalNumber)
                .orElseThrow(() -> new RuntimeException("User not found with personal number: " + userPersonalNumber));

        task.setUser(user);      // link task to patient
        task.setCompleted(false); // new task always starts as not completed
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public AssigneeTask updateTask(AssigneeTask task) {
        AssigneeTask existingTask = taskRepository.findById(task.getId())
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + task.getId()));

        // update allowed fields
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setType(task.getType());
        existingTask.setStartTime(task.getStartTime());
        existingTask.setEndTime(task.getEndTime());
        existingTask.setCompleted(task.isCompleted());

        // allow reassigning the task to another patient if provided
        if (task.getUser() != null) {
            User user = userRepository.findByPersonalNumber(task.getUser().getPersonalNumber())
                    .orElseThrow(() -> new RuntimeException("User not found with personal number: "
                            + task.getUser().getPersonalNumber()));
            existingTask.setUser(user);
        }

        return taskRepository.save(existingTask);
    }

    @Override
    public AssigneeTask deleteTask(Long taskId) {
        AssigneeTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        taskRepository.delete(task);
        return task; // optionally return the deleted task
    }
}
