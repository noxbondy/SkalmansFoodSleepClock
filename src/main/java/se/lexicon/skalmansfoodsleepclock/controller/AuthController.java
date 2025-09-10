package se.lexicon.skalmansfoodsleepclock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import se.lexicon.skalmansfoodsleepclock.Entity.Role;
import se.lexicon.skalmansfoodsleepclock.dto.LoginRequestDto;
import se.lexicon.skalmansfoodsleepclock.dto.RegisterRequestDto;
import se.lexicon.skalmansfoodsleepclock.Entity.User;
import se.lexicon.skalmansfoodsleepclock.dto.UserDto;
import se.lexicon.skalmansfoodsleepclock.repository.UserRepository;
import se.lexicon.skalmansfoodsleepclock.service.AuthService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "https://clock-react.vercel.app")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

@Autowired
    private final AuthService authService;
    @Autowired
    private final UserRepository userRepository;



    // ✅ Registration endpoint
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(
            @RequestBody RegisterRequestDto dto,
            @RequestParam(defaultValue = "USER") String roleName) {

        User user = authService.register(dto, roleName);
        return ResponseEntity.ok(UserDto.fromEntity(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request) {
        try {
            UserDto userDto = authService.login(request);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ✅ Get user by personal number
    @GetMapping("/{personalNumber}")
    public ResponseEntity<UserDto> getUserByPersonalNumber(@PathVariable String personalNumber) {
        UserDto userDto = authService.getUserByPersonalNumber(personalNumber);
        return ResponseEntity.ok(userDto);
    }

    // ✅ Optional: check if email exists (case-insensitive)
    @GetMapping("/email/{email}")
    public ResponseEntity<Boolean> checkEmail(@PathVariable String email) {
        Optional<User> userOpt = authService.findByEmailIgnoreCase(email);
        return ResponseEntity.ok(userOpt.isPresent());
    }

    // ✅ Update user role
    @PutMapping("/users/{personalNumber}/role")
    public ResponseEntity<UserDto> updateUserRole(
            @PathVariable String personalNumber,
            @RequestParam String roleName) {

        User user = userRepository.findByPersonalNumber(personalNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role;
        try {
            role = Role.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + roleName);
        }

        user.setRole(role);
        userRepository.save(user);

        return ResponseEntity.ok(UserDto.fromEntity(user));
    }

    // ✅ Get all users
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userRepository.findAll()
                .stream()
                .map(UserDto::fromEntity)
                .toList();
        return ResponseEntity.ok(users);
    }

    // ✅ Delete user
    @DeleteMapping("/{personalNumber}")
    public ResponseEntity<Void> deleteUser(@PathVariable String personalNumber) {
        authService.deleteUser(personalNumber);
        return ResponseEntity.noContent().build();
    }
}