package se.lexicon.skalmansfoodsleepclock.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mealName;
    private String recipeName;
    private String instructions;
    private String allergyName;
    private String dietName;

    private LocalDateTime mealDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_personal_number", nullable = false)
    private User user;
}
