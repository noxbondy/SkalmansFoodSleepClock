package se.lexicon.skalmansfoodsleepclock.Entity.assigneeTask;

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
@Table(name = "assignee_tasks")
public class AssigneeTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 👈 unique task ID


    //  Which patient the task belongs to
    @Column(nullable = false, length = 100)
    private String title;
    @Column(length = 500)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType type;
    @Column(nullable = false)
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Column(nullable = false)
    private boolean completed = false;    //  mark if done

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_number", nullable = false) // foreign key to User
    private User user;

}
