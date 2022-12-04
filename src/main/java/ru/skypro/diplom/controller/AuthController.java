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
import ru.skypro.diplom.dto.auth.LoginReqDto;
import ru.skypro.diplom.dto.profile.CreateUserDto;
import ru.skypro.diplom.exception.UserAlreadyCreatedException;
import ru.skypro.diplom.service.UsersService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Авторизация", description = "Auth Controller")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDto req) {
        if (usersService.login(req.getUsername(), req.getPassword())) {
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
            return ResponseEntity.ok()
                .build();
        } catch (UserAlreadyCreatedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .build();
        }
    }
}
