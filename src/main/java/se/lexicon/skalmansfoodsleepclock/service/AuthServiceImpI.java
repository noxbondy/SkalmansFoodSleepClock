package se.lexicon.skalmansfoodsleepclock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.skalmansfoodsleepclock.dto.LoginRequestDto;
import se.lexicon.skalmansfoodsleepclock.dto.RegisterRequestDto;
import se.lexicon.skalmansfoodsleepclock.Entity.Role;
import se.lexicon.skalmansfoodsleepclock.Entity.User;
import se.lexicon.skalmansfoodsleepclock.dto.UserDto;
import se.lexicon.skalmansfoodsleepclock.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpI implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public User register(RegisterRequestDto dto, String roleName) {
        if (userRepository.existsByPersonalNumber(dto.personalNumber())) {
            throw new RuntimeException("Personal number already exists");
        }
        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email already exists");
        }

        Role role;
        try {
            role = Role.valueOf(roleName.toUpperCase()); // Convert string to enum
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + roleName);
        }

        User user = User.builder()
                .personalNumber(dto.personalNumber())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .dateOfBirth(dto.dateOfBirth())
                .gender(dto.gender())
                .phoneNumber(dto.phoneNumber())
                .address(dto.address())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password())) // ✅ encode password
                .role(role) // ✅ assign role
                .build();

        return userRepository.save(user);
    }

    @Override
    public UserDto login(LoginRequestDto request) {
        // Authenticate credentials (Spring Security)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // Fetch user from DB
        User user = findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // ✅ Convert User -> UserDto
        return UserDto.fromEntity(user);
    }

    @Override
    public UserDto getUserByPersonalNumber(String personalNumber) {
        return userRepository.findByPersonalNumber(personalNumber)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Optional<User> findByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .map(Optional::of)
                .orElse(Optional.empty());
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDto::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public UserDto updateUserRole(String personalNumber, Role role) {
        User user = userRepository.findByPersonalNumber(personalNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        userRepository.save(user);
        return UserDto.fromEntity(user);
    }
}





