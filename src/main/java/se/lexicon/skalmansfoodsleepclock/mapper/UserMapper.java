package se.lexicon.skalmansfoodsleepclock.mapper;

import org.springframework.stereotype.Component;
import se.lexicon.skalmansfoodsleepclock.Entity.User;
import se.lexicon.skalmansfoodsleepclock.dto.RegisterRequestDto;
import se.lexicon.skalmansfoodsleepclock.dto.UserInsertDto;

@Component
public class UserMapper {

    // Convert User entity to UserInsertDto (for creation purposes)
    public RegisterRequestDto fromEntity(User user) {
        if (user == null) return null;
        return new RegisterRequestDto(
                user.getPersonalNumber(),
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth(),
                user.getGender(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    // Convert RegisterRequestDto to User entity (for saving)
    public User toEntity(RegisterRequestDto dto) {
        if (dto == null) return null;
        return dto.toEntity();
    }
}
