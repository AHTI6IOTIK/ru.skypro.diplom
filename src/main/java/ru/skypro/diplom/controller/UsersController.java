package ru.skypro.diplom.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.diplom.dto.auth.NewPasswordDto;
import ru.skypro.diplom.dto.profile.CreateUserDto;
import ru.skypro.diplom.dto.profile.ResponseWrapperUserDto;
import ru.skypro.diplom.dto.profile.UserDto;
import ru.skypro.diplom.exception.UserAlreadyCreatedException;
import ru.skypro.diplom.service.UsersService;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/users")
@Tag(name = "Пользователи", description = "User Controller")
@ApiResponses(
    value = {
        @ApiResponse(responseCode = "401", content = @Content()),
        @ApiResponse(responseCode = "403", content = @Content())
    }
)
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/add")
    @Operation(
        summary = "addUser",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "user"),
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "201", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    public CreateUserDto addUser(
        @RequestBody CreateUserDto updatedUserDto
    ) {
        try {
            CreateUserDto userDto = usersService.createUser(updatedUserDto);
            if (null == userDto) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            return userDto;
        } catch (UserAlreadyCreatedException exception) {
            throw new ResponseStatusException(HttpStatus.CREATED);
        }
    }

    @GetMapping
    @Operation(
        summary = "getUsers",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "user"),
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "201", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    public ResponseWrapperUserDto getUsers() {
        ResponseWrapperUserDto wrapperUserDto = usersService.getUsers();
        if (null == wrapperUserDto) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return wrapperUserDto;
    }

    @PatchMapping("/{userId}")
    @Operation(
        summary = "updateUser",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "user"),
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "204", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    public UserDto updateUser(
        @PathVariable long userId,
        @RequestBody UserDto updatedUser
    ) {
        UserDto user = usersService.findById(userId);
        if (null == user) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        boolean isUpdated = usersService.updateUser(userId, user, updatedUser);
        if (!isUpdated) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return updatedUser;
    }

    @PostMapping("/set_password")
    @Operation(
        summary = "setPassword",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "newPassword"),
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "201", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    public NewPasswordDto setPassword(
        @RequestBody NewPasswordDto newPasswordDto
    ) {
        boolean isUpdated = usersService.setPassword(newPasswordDto);
        if (!isUpdated) {
            throw new ResponseStatusException(HttpStatus.CREATED);
        }

        return newPasswordDto;
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "getUser",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "newPassword"),
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    public UserDto getUser(
        @PathVariable("id") long userId
    ) {
        UserDto user = usersService.findById(userId);
        if (null == user) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return user;
    }
}
