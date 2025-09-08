package se.lexicon.skalmansfoodsleepclock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.skalmansfoodsleepclock.Entity.reminder.Reminder;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long>{

    List<Reminder> findByUserPersonalNumber(String personalNumber);


}
