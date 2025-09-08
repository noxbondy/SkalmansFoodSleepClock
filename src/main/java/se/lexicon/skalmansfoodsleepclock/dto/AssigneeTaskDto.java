package se.lexicon.skalmansfoodsleepclock.dto;

import se.lexicon.skalmansfoodsleepclock.Entity.assigneeTask.AssigneeTask;
import se.lexicon.skalmansfoodsleepclock.Entity.assigneeTask.TaskType;

import java.time.LocalDateTime;

public record AssigneeTaskDto(
        Long id,
        String title,
        String description,
        TaskType type,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean completed,
        String userPersonalNumber
) {
    public static AssigneeTaskDto fromEntity(AssigneeTask task) {
        return new AssigneeTaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getType(),
                task.getStartTime(),
                task.getEndTime(),
                task.isCompleted(),
                task.getUser() != null ? task.getUser().getPersonalNumber() : null
        );
    }
}
