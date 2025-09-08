package se.lexicon.skalmansfoodsleepclock.service;

import se.lexicon.skalmansfoodsleepclock.dto.MealDto;

import java.util.List;

public interface MealService {


    MealDto createMeal(String personalNumber, MealDto mealDto);
    List<MealDto> getMealsByUser(String personalNumber);
    MealDto updateMeal(Long mealId, MealDto mealDto);
    void deleteMeal(Long mealId);
}
