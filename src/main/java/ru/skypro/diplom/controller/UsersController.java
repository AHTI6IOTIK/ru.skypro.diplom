package ru.skypro.diplom.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.diplom.dto.auth.NewPasswordDto;
import ru.skypro.diplom.dto.profile.CreateUserDto;
import ru.skypro.diplom.dto.profile.ResponseWrapperUserDto;
import ru.skypro.diplom.dto.profile.UserDto;
import ru.skypro.diplom.exception.CurrentPasswordNotEqualsException;
import ru.skypro.diplom.exception.UserAlreadyCreatedException;
import ru.skypro.diplom.exception.UserNotFoundException;
import ru.skypro.diplom.service.UsersService;

import javax.annotation.security.RolesAllowed;

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

    @PostMapping
    @Operation(
        summary = "addUser",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "user"),
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "201", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    @RolesAllowed("ADMIN")
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
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "201", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    @RolesAllowed("ADMIN")
    public ResponseWrapperUserDto getUsers() {
        ResponseWrapperUserDto wrapperUserDto = usersService.getUsers();
        if (null == wrapperUserDto) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return wrapperUserDto;
    }

    @GetMapping("/me")
    @Operation(
        summary = "getMe",
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "201", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    @RolesAllowed("USER")
    public UserDto getMe(
        Authentication authentication
    ) {
        try {
            return usersService.getMeByLogin(authentication.getName());
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/me")
    @Operation(
        summary = "updateUser",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "user"),
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "204", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    @RolesAllowed({"USER"})
    public UserDto updateUser(
        @RequestBody UserDto updatedUser,
        Authentication authentication
    ) {
        try {
            return usersService.updateUser(
                authentication.getName(),
                updatedUser
            );
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
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
    @RolesAllowed({"USER"})
    public NewPasswordDto setPassword(
        @RequestBody NewPasswordDto newPasswordDto,
        Authentication authentication
    ) {
        try {
            return usersService.changePassword(
                authentication.getName(),
                newPasswordDto
            );
        } catch (CurrentPasswordNotEqualsException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "getUser",
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    public UserDto getUser(
        @PathVariable("id") long userId
    ) {
        try {
            return usersService.findById(userId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
