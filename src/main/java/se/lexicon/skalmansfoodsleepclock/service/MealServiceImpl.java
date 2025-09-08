package se.lexicon.skalmansfoodsleepclock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.skalmansfoodsleepclock.Entity.Meal;
import se.lexicon.skalmansfoodsleepclock.Entity.User;
import se.lexicon.skalmansfoodsleepclock.dto.MealDto;
import se.lexicon.skalmansfoodsleepclock.repository.MealRepository;
import se.lexicon.skalmansfoodsleepclock.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public MealDto createMeal(String personalNumber, MealDto mealDto) {
        User user = userRepository.findById(personalNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Meal meal = mealDto.toEntity(user);
        user.getMeals().add(meal);

        userRepository.save(user); // cascade saves meal
        return MealDto.fromEntity(meal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MealDto> getMealsByUser(String personalNumber) {
        User user = userRepository.findById(personalNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getMeals().stream()
                .map(MealDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MealDto updateMeal(Long mealId, MealDto mealDto) {
        Meal existingMeal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found"));

        existingMeal.setMealName(mealDto.mealName());
        existingMeal.setRecipeName(mealDto.recipeName());
        existingMeal.setInstructions(mealDto.instructions());
        existingMeal.setAllergyName(mealDto.allergyName());
        existingMeal.setDietName(mealDto.dietName());
        existingMeal.setMealDateTime(mealDto.mealDateTime());

        mealRepository.save(existingMeal);

        return MealDto.fromEntity(existingMeal);
    }

    @Override
    @Transactional
    public void deleteMeal(Long mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found"));

        mealRepository.delete(meal);
    }
    }

