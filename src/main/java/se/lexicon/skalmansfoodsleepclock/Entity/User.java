package se.lexicon.skalmansfoodsleepclock.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se.lexicon.skalmansfoodsleepclock.Entity.assigneeTask.AssigneeTask;
import se.lexicon.skalmansfoodsleepclock.Entity.reminder.Reminder;
;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "personal_number", length = 12, nullable = false, unique = true)
    private String personalNumber;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String phoneNumber;

    private String address;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.FAMILY;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meal> meals = new ArrayList<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reminder> reminders = new ArrayList<>();


    //  One -to- Many Relation to User /patient
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssigneeTask> tasks= new ArrayList<>();

   // * ==========================
   // UserDetails implementation
    //   ========================== */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "ROLE_" + role.name());
    }

    @Override
    public String getUsername() {
        return email; // Spring Security uses this as login username
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // adjust if you add expiration logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // adjust if you add lock logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // adjust if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // adjust if you add enable/disable logic
    }
}
