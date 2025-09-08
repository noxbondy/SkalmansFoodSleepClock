package se.lexicon.skalmansfoodsleepclock.dto;

import se.lexicon.skalmansfoodsleepclock.Entity.Meal;
import se.lexicon.skalmansfoodsleepclock.Entity.User;

import java.time.LocalDateTime;

public record MealDto(
        Long id,
        String personalNumber, // link to User
        String mealName,
        String recipeName,
        String instructions,
        String allergyName,
        String dietName,
        LocalDateTime mealDateTime
) {
    public Meal toEntity(User user) {
        Meal meal = new Meal();
        meal.setMealName(mealName);
        meal.setRecipeName(recipeName);
        meal.setInstructions(instructions);
        meal.setAllergyName(allergyName);
        meal.setDietName(dietName);
        meal.setMealDateTime(mealDateTime);
        meal.setUser(user);
        return meal;
    }

    public static MealDto fromEntity(Meal meal) {
        return new MealDto(
                meal.getId(),
                meal.getUser().getPersonalNumber(),
                meal.getMealName(),
                meal.getRecipeName(),
                meal.getInstructions(),
                meal.getAllergyName(),
                meal.getDietName(),
                meal.getMealDateTime()
        );
    }
}
