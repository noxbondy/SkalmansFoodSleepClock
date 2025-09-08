package se.lexicon.skalmansfoodsleepclock.dto;

import java.util.Set;

public record UserWithMealsDto(
        Long id,
        String personalNumber,
        String firstName,
        String lastName,
        String dateOfBirth,
        String gender,
        String phoneNumber,
        String address,
        String email,
        Set<String> roles,
        Set<MealDto> meals
) {
}
