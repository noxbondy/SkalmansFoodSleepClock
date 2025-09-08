package se.lexicon.skalmansfoodsleepclock.service;

import se.lexicon.skalmansfoodsleepclock.Entity.assigneeTask.AssigneeTask;
import se.lexicon.skalmansfoodsleepclock.dto.UserDto;

import java.util.List;

public interface AssigneeTaskService {

    UserDto getPatientWithTasks(String personalNumber);
    List<AssigneeTask> getAllTasks();
    AssigneeTask completeTask(Long taskId);
    AssigneeTask createTask(AssigneeTask task, String userPersonalNumber);

AssigneeTask deleteTask(Long taskId);
AssigneeTask updateTask(AssigneeTask task);
}
