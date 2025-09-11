package se.lexicon.skalmansfoodsleepclock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.skalmansfoodsleepclock.dto.MealDto;
import se.lexicon.skalmansfoodsleepclock.service.MealService;


import java.util.List;
@CrossOrigin(origins = "https://skalman-clock-aa66170cc60e.herokuapp.com")
@RestController
@RequestMapping("/meals")
@RequiredArgsConstructor
public class MealController {


    private final MealService mealService;

    @PostMapping("/{personalNumber}")
    public ResponseEntity<MealDto> createMeal(
            @PathVariable String personalNumber,
            @RequestBody MealDto mealDto) {
        MealDto created = mealService.createMeal(personalNumber, mealDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{personalNumber}")
    public ResponseEntity<List<MealDto>> getMeals(@PathVariable String personalNumber) {
        List<MealDto> meals = mealService.getMealsByUser(personalNumber);
        return ResponseEntity.ok(meals);
    }

    @PutMapping("/{mealId}")
    public ResponseEntity<MealDto> updateMeal(
            @PathVariable Long mealId,
            @RequestBody MealDto mealDto) {
        MealDto updated = mealService.updateMeal(mealId, mealDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long mealId) {
        mealService.deleteMeal(mealId);
        return ResponseEntity.noContent().build();
    }



}