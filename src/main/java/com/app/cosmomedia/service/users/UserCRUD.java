package com.app.cosmomedia.service.users;

import com.app.cosmomedia.dto.UsersDTO;
import com.app.cosmomedia.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.Optional;

public interface UserCRUD {

    /**
     * Retrieves a paginated list of users.
     *
     * @param pageable Pagination information.
     * @return A Page containing the list of users.
     */
    Page<Users> getUsersList(Pageable pageable);

    /**
     * Adds a new user based on the provided UsersDTO.
     *
     * @param usersDTO User data to create a new user.
     * @return A message indicating the success or failure of the operation.
     */
    String addUser(Users usersDTO) throws MessagingException;

    /**
     * Updates an existing user.
     *
     * @param users The user entity with updated information.
     * @return A message indicating the success or failure of the operation.
     */
    String updateUser(Users users);

    /**
     * Soft deletes a user based on the provided CIN (Customer Identification Number).
     *
     * @param CIN The Customer Identification Number of the user to be soft-deleted.
     * @return A message indicating the success or failure of the operation.
     */
    String softDeleteUser(String CIN);

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
     * @return A Page containing the filtered users.
     */
    Page<Users> filterUsers(
            String firstName,
            String lastName,
            Date startDate,
            Date endDate,
            String email,
            String role,
            String deletedBy
    );
}
