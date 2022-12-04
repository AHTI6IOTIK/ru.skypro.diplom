package ru.skypro.diplom.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.skypro.diplom.dto.auth.NewPasswordDto;
import ru.skypro.diplom.dto.profile.CreateUserDto;
import ru.skypro.diplom.dto.profile.ResponseWrapperUserDto;
import ru.skypro.diplom.dto.profile.UserDto;
import ru.skypro.diplom.entity.UserEntity;
import ru.skypro.diplom.exception.CurrentPasswordNotEqualsException;
import ru.skypro.diplom.exception.UserAlreadyCreatedException;
import ru.skypro.diplom.exception.UserNotFoundException;
import ru.skypro.diplom.mapping.user.CreateUserDtoMapper;
import ru.skypro.diplom.mapping.user.UserDtoMapper;
import ru.skypro.diplom.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    private final FileService fileService;
    private final UserRepository userRepository;
    private final CreateUserDtoMapper createUserDtoMapper;
    private final UserDtoMapper userDtoMapper;

    public UsersService(
        FileService fileService,
        UserRepository userRepository,
        CreateUserDtoMapper createUserDtoMapper,
        UserDtoMapper userDtoMapper
    ) {
        this.fileService = fileService;
        this.userRepository = userRepository;
        this.createUserDtoMapper = createUserDtoMapper;
        this.userDtoMapper = userDtoMapper;
    }

    public UserEntity getUserByLogin(String userEmail) {
        return userRepository.findByEmail(userEmail)
            .orElseThrow(UserNotFoundException::new);
    }

    public UserDto getMeByLogin(String login) {
        UserEntity user = getUserByLogin(login);

        return userDtoMapper.toDto(user);
    }

    public CreateUserDto createUser(CreateUserDto createUserDto) {

        int countUser = userRepository.countByEmail(createUserDto.getUsername());
        if (countUser > 0) {
            throw new UserAlreadyCreatedException(createUserDto.getUsername());
        }

        UserEntity user = createUserDtoMapper.toModel(createUserDto);
        UserEntity createdUser = userRepository.save(user);

        return createUserDtoMapper.toDto(createdUser);
    }

    public ResponseWrapperUserDto getUsers() {
        List<UserEntity> usersList = userRepository.findAll();
        if (usersList.size() == 0) {
            return null;
        }

        ResponseWrapperUserDto wrapperUserDto = new ResponseWrapperUserDto();
        wrapperUserDto.setCount(usersList.size());
        wrapperUserDto.setResult(
            userDtoMapper.toUserDtoList(usersList)
                .toArray(new UserDto[0])
        );

        return wrapperUserDto;
    }

    public UserDto findById(long userId) {
        return userDtoMapper.toDto(
            userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new)
        );
    }

    public UserDto updateUser(
        String userLogin,
        UserDto updatedUser
    ) {
        UserEntity user = getUserByLogin(userLogin);

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPhone(updatedUser.getPhone());

        return userDtoMapper.toDto(
            userRepository.save(user)
        );
    }

    public NewPasswordDto changePassword(
        String userLogin,
        NewPasswordDto newPasswordDto
    ) {
        UserEntity userEntity = getUserByLogin(userLogin);

        if (!userEntity.getPassword()
            .equals(newPasswordDto.getCurrentPassword())
        ) {
            throw new CurrentPasswordNotEqualsException();
        }

        userEntity.setPassword(newPasswordDto.getNewPassword());
        return newPasswordDto;
    }

    public boolean updateUserAvatarPath(
        String userLogin,
        String filePath
    ) {
        UserEntity user = getUserByLogin(userLogin);
        Optional<String> optionalAvatar = Optional.ofNullable(user.getAvatar());

        optionalAvatar.ifPresent(oldAvatar -> {
                if (!oldAvatar.isEmpty()) {
                    try {
                        fileService.removeFileByPath(oldAvatar);
                    } catch (IOException ignored) {}
                }
            }
        );

        user.setAvatar(filePath);

        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            return false;
        }

        return true;
    }
}
