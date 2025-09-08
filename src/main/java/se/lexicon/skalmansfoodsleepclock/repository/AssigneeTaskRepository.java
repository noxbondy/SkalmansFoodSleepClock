package se.lexicon.skalmansfoodsleepclock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.skalmansfoodsleepclock.Entity.assigneeTask.AssigneeTask;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssigneeTaskRepository extends JpaRepository<AssigneeTask, Long> {
    List<AssigneeTask> findByUserPersonalNumber(String personalNumber);

}
