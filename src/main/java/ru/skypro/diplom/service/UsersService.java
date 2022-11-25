package ru.skypro.diplom.service;

import org.springframework.stereotype.Service;
import ru.skypro.diplom.dto.auth.NewPasswordDto;
import ru.skypro.diplom.dto.profile.CreateUserDto;
import ru.skypro.diplom.dto.profile.ResponseWrapperUserDto;
import ru.skypro.diplom.dto.profile.UserDto;
import ru.skypro.diplom.factory.UserFactory;

@Service
public class UsersService {
    private final UserFactory userFactory;

    public UsersService(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    public CreateUserDto createUser(CreateUserDto updatedUserDto) {
        return userFactory.createCreateUser(
            "email",
            "firstName",
            "lastName",
            "password",
            "phone"
        );
    }

    public ResponseWrapperUserDto getUsers() {
        ResponseWrapperUserDto wrapperUserDto = new ResponseWrapperUserDto();
        wrapperUserDto.setCount(3);
        wrapperUserDto.setResult(new UserDto[]{
            userFactory.createUser(
                "email",
                "firstName",
                1L,
                "lastName",
                "phone"
            ),
            userFactory.createUser(
                "email",
                "firstName",
                2L,
                "lastName",
                "phone"
            ),
            userFactory.createUser(
                "email",
                "firstName",
                3L,
                "lastName",
                "phone"
            ),
        });

        return wrapperUserDto;
    }

    public UserDto findById(long userId) {
        return userFactory.createUser(
            "email",
            "firstName",
            userId,
            "lastName",
            "phone"
        );
    }

    public boolean updateUser(long userId, UserDto user, UserDto updatedUser) {
        return true;
    }

    public boolean setPassword(NewPasswordDto newPasswordDto) {
        return false;
    }
}
