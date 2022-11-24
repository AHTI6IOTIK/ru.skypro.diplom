package ru.skypro.diplom.factory;

import org.springframework.stereotype.Component;
import ru.skypro.diplom.dto.profile.CreateUserDto;
import ru.skypro.diplom.dto.profile.UserDto;

@Component
public class UserFactory {
    public CreateUserDto createCreateUser(
        String email,
        String firstName,
        String lastName,
        String password,
        String phone
    ) {
        CreateUserDto userDto = new CreateUserDto();

        userDto.setEmail(email);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setPassword(password);
        userDto.setPhone(phone);

        return userDto;
    }

    public UserDto createUser(
        String email,
        String firstName,
        long id,
        String lastName,
        String phone
    ) {
        UserDto user = new UserDto();

        user.setEmail(email);
        user.setFirstName(firstName);
        user.setId(id);
        user.setLastName(lastName);
        user.setPhone(phone);

        return user;
    }
}
