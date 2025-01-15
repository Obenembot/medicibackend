package za.co.medici.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.medici.dto.CreateUserDto;
import za.co.medici.dto.UseDto;
import za.co.medici.exceptions.UserException;
import za.co.medici.models.User;
import za.co.medici.service.UserService;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3000/"})
@Tag(name = "User Management", description = "API for managing users (Create, Update, Get and Delete")
@RestController
@RequestMapping("/api/users")
public class UserController {

    public final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/")
    @Operation(summary = "Create User", description = "Create User. returns 200 status is no issue, 400 in case of bad data and user already exist")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto useDto, @RequestHeader("X-Username") String usernameHeader) throws UserException {
        User createdUser = userService.createUser(useDto, usernameHeader);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/")
    @Operation(summary = "Update User", description = "Update User. returns 200 status is no issue, 400 in case of bad data, and 404 in case the user does not exist")
    public ResponseEntity<User> updateUser(@RequestBody UseDto useDto, @RequestHeader("X-Username") String usernameHeader) throws UserException {
        User updatedUser = userService.updateUser(useDto, usernameHeader);
        return ResponseEntity.ok(updatedUser);
    }


    @GetMapping("/{email}")
    @Operation(summary = "Find User By Email", description = "Retrieves User by email. returns 200 status is no issue")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email) throws UserException {
        User user = userService.findUserByEmailAndDeletedDateNull(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/")
    @Operation(summary = "Find All Users", description = "Retrieves All Users. returns 200 status is no issue")
    public ResponseEntity<Page<User>> findUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.findAllUserAndDeletedDateNull(pageable));
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Delete User By Email", description = "Delete User by email. returns 200 status is no issue and 404 if user not found")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email, @RequestHeader("X-Username") String usernameHeader) throws UserException {
        userService.deleteUserByEmail(email, usernameHeader);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{email}/{oldPassword}/{newPassword}")
    @Operation(summary = "Update User Password By Email ", description = "Update User Password By Email. returns 200 status is no issue and 404 if user not found")
    public ResponseEntity<User> updateUserPassword(@PathVariable String email, @PathVariable String oldPassword, @PathVariable String newPassword) throws UserException {
        return ResponseEntity.ok(userService.updateUserPassword(email, oldPassword, newPassword));
    }

}
