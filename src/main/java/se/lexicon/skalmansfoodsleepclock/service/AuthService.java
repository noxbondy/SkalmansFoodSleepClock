package se.lexicon.skalmansfoodsleepclock.service;

import se.lexicon.skalmansfoodsleepclock.Entity.Role;
import se.lexicon.skalmansfoodsleepclock.dto.LoginRequestDto;
import se.lexicon.skalmansfoodsleepclock.dto.RegisterRequestDto;
import se.lexicon.skalmansfoodsleepclock.Entity.User;
import se.lexicon.skalmansfoodsleepclock.dto.UserDto;

import java.util.List;
import java.util.Optional;


public interface AuthService {
    User register(RegisterRequestDto dto, String roleName);
    UserDto login(LoginRequestDto request);
    UserDto getUserByPersonalNumber(String personalNumber);
    Optional<User> findByEmailIgnoreCase(String email);
    UserDto updateUserRole(String personalNumber, Role role);
    List<UserDto> findAllUsers();
}


