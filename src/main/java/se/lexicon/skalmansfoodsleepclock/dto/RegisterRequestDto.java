package se.lexicon.skalmansfoodsleepclock.dto;

import se.lexicon.skalmansfoodsleepclock.Entity.Role;
import se.lexicon.skalmansfoodsleepclock.Entity.User;

import java.time.LocalDate;

public record RegisterRequestDto(

        String personalNumber,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String gender,
        String phoneNumber,
        String address,
        String email,
        String password,
        Role role
) {
    public User toEntity() {
        User user = new User();
        user.setPersonalNumber(this.personalNumber());
        user.setFirstName(this.firstName());
        user.setLastName(this.lastName());
        user.setDateOfBirth(this.dateOfBirth());
        user.setGender(this.gender());
        user.setPhoneNumber(this.phoneNumber());
        user.setAddress(this.address());
        user.setEmail(this.email());
        user.setPassword(this.password());
        user.setRole(this.role());
        return user;
    }
}

