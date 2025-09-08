package se.lexicon.skalmansfoodsleepclock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.skalmansfoodsleepclock.Entity.assigneeTask.AssigneeTask;
import se.lexicon.skalmansfoodsleepclock.dto.AssigneeTaskDto;
import se.lexicon.skalmansfoodsleepclock.dto.UserDto;
import se.lexicon.skalmansfoodsleepclock.service.AssigneeTaskService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class AssigneeTaskController {

    private final AssigneeTaskService taskService;

    // ✅ Create a new task for a patient (nurse assigns)
    @PostMapping("/patient/{personalNumber}")
    public ResponseEntity<AssigneeTaskDto> createTask(
            @PathVariable String personalNumber,
            @RequestBody AssigneeTaskDto taskDto
    ) {
        AssigneeTask newTask = new AssigneeTask();
        newTask.setTitle(taskDto.title());
        newTask.setDescription(taskDto.description());
        newTask.setType(taskDto.type());
        newTask.setStartTime(taskDto.startTime());
        newTask.setEndTime(taskDto.endTime());
        newTask.setCompleted(false); // newly assigned = not completed

        AssigneeTask saved = taskService.createTask(newTask, personalNumber);
        return ResponseEntity.ok(AssigneeTaskDto.fromEntity(saved));
    }

    // ✅ Get all tasks (DTOs only)
    @GetMapping
    public ResponseEntity<List<AssigneeTaskDto>> getAllTasks() {
        List<AssigneeTaskDto> tasks = taskService.getAllTasks()
                .stream()
                .map(AssigneeTaskDto::fromEntity)
                .toList();
        return ResponseEntity.ok(tasks);
    }

    // ✅ Get patient with their tasks
    @GetMapping("/patient/{personalNumber}")
    public ResponseEntity<UserDto> getPatientWithTasks(@PathVariable String personalNumber) {
        return ResponseEntity.ok(taskService.getPatientWithTasks(personalNumber));
    }

    // ✅ Mark a task as completed (return DTO, not entity)
    @PutMapping("/{taskId}/complete")
    public ResponseEntity<AssigneeTaskDto> completeTask(@PathVariable Long taskId) {
        AssigneeTask completedTask = taskService.completeTask(taskId);
        return ResponseEntity.ok(AssigneeTaskDto.fromEntity(completedTask));
    }


    // ✅ Update task
    @PutMapping("/{taskId}")
    public ResponseEntity<AssigneeTask> updateTask(
            @PathVariable Long taskId,
            @RequestBody AssigneeTask task
    ) {
        task.setId(taskId); // make sure correct ID is used
        AssigneeTask updated = taskService.updateTask(task);
        return ResponseEntity.ok(updated);
    }

    // ✅ Delete task
    @DeleteMapping("/{taskId}")
    public ResponseEntity<AssigneeTask> deleteTask(@PathVariable Long taskId) {
        AssigneeTask deleted = taskService.deleteTask(taskId);
        return ResponseEntity.ok(deleted);
    }
}
