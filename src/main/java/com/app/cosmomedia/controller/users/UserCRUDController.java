package com.app.cosmomedia.controller.users;

import com.app.cosmomedia.entity.Users;
import com.app.cosmomedia.service.users.UserCRUD;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Controller class for managing user operations.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCRUDController {

    private final UserCRUD userCRUD;

    /**
     * Retrieves a paginated list of all users.
     *
     * @param page Page number.
     * @param size Number of items per page.
     * @return Paginated list of users.
     */
    @GetMapping
    public Page<Users> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return userCRUD.getUsersList(pageable);
    }

    /**
     * Retrieves a user by their Customer Identification Number (CIN).
     *
     * @param CIN The Customer Identification Number of the user.
     * @return The user if found, otherwise null.
     */
    @GetMapping("/user/{CIN}")
    public Users getOneUser(@PathVariable String CIN) {
        return userCRUD.getOneUser(CIN);
    }

    /**
     * Retrieves a paginated list of deleted users.
     *
     * @param page Page number.
     * @param size Number of items per page.
     * @return Paginated list of deleted users.
     */
    @GetMapping("/deleted")
    public Page<Users> getDeletedUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return userCRUD.getDeletedUsersList(pageable);
    }

    /**
     * Adds a new user.
     *
     * @param users The user to be added.
     * @return ResponseEntity with a success message or an error message.
     */
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody Users users) {
        try {
            String result = userCRUD.addUser(users);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding user: " + e.getMessage());
        }
    }

    /**
     * Updates an existing user.
     *
     * @param users The user to be updated.
     * @return ResponseEntity with a success message or an error message.
     */
    @PatchMapping
    public ResponseEntity<String> updateUser(@RequestBody Users users) {
        try {
            String result = userCRUD.updateUser(users);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user: " + e.getMessage());
        }
    }

    /**
     * Soft deletes a user based on their unique identifier (CIN).
     *
     * @param cin The CIN (Customer Identification Number) of the user to be deleted.
     * @return ResponseEntity with a success message or an error message.
     */
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam(name = "cin", required = true) String cin) {
        try {
            String result = userCRUD.softDeleteUser(cin);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user: " + e.getMessage());
        }
    }

    /**
     * Restore a user based on their unique identifier (CIN).
     *
     * @param cin The CIN (Customer Identification Number) of the user to be deleted.
     * @return ResponseEntity with a success message or an error message.
     */
    @PatchMapping("/restore")
    public ResponseEntity<String> restoreUser(@RequestParam String cin) {
        try {
            String result = userCRUD.restorDeleteUser(cin);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error restoring user: " + e.getMessage());
        }
    }

    /**
     * Retrieves a paginated list of users based on specified filter criteria.
     *
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param startDate The start date for filtering.
     * @param endDate   The end date for filtering.
     * @param email     The email address of the user.
     * @param role      The role of the user.
     * @param deletedBy The user who performed the deletion.
     * @return Paginated list of users matching the specified criteria.
     */
    @GetMapping("/get/filter")
    public Page<Users> filterUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String deletedBy,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return userCRUD.filterUsers(firstName, lastName, startDate, endDate, email, role, deletedBy, pageable);
    }
}
