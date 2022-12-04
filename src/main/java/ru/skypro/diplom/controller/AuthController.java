package ru.skypro.diplom.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.diplom.dto.auth.LoginReqDto;
import ru.skypro.diplom.dto.profile.CreateUserDto;
import ru.skypro.diplom.exception.UserAlreadyCreatedException;
import ru.skypro.diplom.mapping.user.RegReqDtoMapper;
import ru.skypro.diplom.service.AuthService;
import ru.skypro.diplom.service.UsersService;

import static ru.skypro.diplom.dto.profile.RoleEnum.USER;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Авторизация", description = "Auth Controller")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UsersService usersService;
    private final RegReqDtoMapper regReqDtoMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDto req) {
        if (authService.login(req.getUsername(), req.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
        @RequestBody CreateUserDto createUserDto
    ) {
        try {
            usersService.createUser(createUserDto);
            if (
                authService.register(
                    regReqDtoMapper.fromCreateUser(createUserDto, USER),
                    USER
                )
            ) {
                return ResponseEntity.ok()
                    .build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
            }
        } catch (UserAlreadyCreatedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
