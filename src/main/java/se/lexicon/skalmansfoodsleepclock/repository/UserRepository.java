package se.lexicon.skalmansfoodsleepclock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.lexicon.skalmansfoodsleepclock.Entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByPersonalNumber(String personalNumber);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPersonalNumber(String personalNumber);

    // ✅ Fetch user with meals and reminders (lists)
    @Query("""
            SELECT u FROM User u
            LEFT JOIN FETCH u.meals
            LEFT JOIN FETCH u.reminders
            WHERE u.personalNumber = :personalNumber
            """)
    Optional<User> findByPersonalNumberWithMealsAndReminders(@Param("personalNumber") String personalNumber);

    Optional<User> findByEmailIgnoreCase(String email);
}
