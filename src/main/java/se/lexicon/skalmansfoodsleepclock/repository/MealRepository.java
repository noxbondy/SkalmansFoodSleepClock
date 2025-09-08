package se.lexicon.skalmansfoodsleepclock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.lexicon.skalmansfoodsleepclock.Entity.Meal;

import java.util.List;


@Repository
public interface MealRepository extends JpaRepository<Meal, Long> { // Meal PK = personalNumber


    List<Meal> findByUserPersonalNumber(String personalNumber);

    String id(Long id);
}
