package se.lexicon.skalmansfoodsleepclock.Entity.reminder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.lexicon.skalmansfoodsleepclock.Entity.User;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reminders")
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReminderType type;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Recurrence recurrence;

    private String soundTheme;
    private String visualTheme;

    @Column(nullable = false)
    private boolean active = true;


    //  Many reminders can belong to one User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_personal_number", nullable = false)
    private User user;
}
