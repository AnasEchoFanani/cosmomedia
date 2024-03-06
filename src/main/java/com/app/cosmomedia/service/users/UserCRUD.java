package com.app.cosmomedia.service.users;

import com.app.cosmomedia.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;

/**
 * Service interface for managing user operations.
 */
public interface UserCRUD {

    /**
     * Retrieves a paginated list of users.
     *
     * @param pageable Pagination information.
     * @return A Page containing the list of users.
     */
    Page<Users> getUsersList(Pageable pageable);

    /**
     * Retrieves a paginated list of deleted users.
     *
     * @param pageable Pagination information.
     * @return A Page containing the list of deleted users.
     */
    Page<Users> getDeletedUsersList(Pageable pageable);

    /**
     * Retrieves a single user by Customer Identification Number (CIN).
     *
     * @param cin Customer Identification Number.
     * @return The user with the specified CIN.
     */
    Users getOneUser(String cin);

    /**
     * Adds a new user based on the provided user entity.
     *
     * @param user User entity containing data for the new user.
     * @return A message indicating the success or failure of the operation.
     * @throws MessagingException If an error occurs during email communication.
     * @throws IOException       If an error occurs during file I/O.
     */
    String addUser(Users user) throws MessagingException, IOException;

    /**
     * Updates an existing user.
     *
     * @param user The user entity with updated information.
     * @return A message indicating the success or failure of the operation.
     */
    String updateUser(Users user);

    /**
     * Soft deletes a user based on the provided Customer Identification Number (CIN).
     *
     * @param cin Customer Identification Number of the user to be soft-deleted.
     * @return A message indicating the success or failure of the operation.
     */
    String softDeleteUser(String cin);

    /**
     * Restores a previously deleted user based on the provided Customer Identification Number (CIN).
     *
     * @param cin Customer Identification Number of the user to be restored.
     * @return A message indicating the success or failure of the operation.
     */
    String restorDeleteUser(String cin);

    /**
     * Filters users based on specified criteria.
     *
     * @param firstName Optional parameter for filtering by first name.
     * @param lastName  Optional parameter for filtering by last name.
     * @param startDate Optional parameter for filtering by start date.
     * @param endDate   Optional parameter for filtering by end date.
     * @param email     Optional parameter for filtering by email.
     * @param role      Optional parameter for filtering by role.
     * @param deletedBy Optional parameter for filtering by deletion performed by.
     * @param pageable  Pagination information.
     * @return A Page containing the filtered users.
     */
    Page<Users> filterUsers(
            String firstName,
            String lastName,
            Date startDate,
            Date endDate,
            String email,
            String role,
            String deletedBy,
            Pageable pageable
    );
}
