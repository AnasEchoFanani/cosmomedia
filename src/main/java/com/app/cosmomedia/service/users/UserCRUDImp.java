package com.app.cosmomedia.service.users;

import com.app.cosmomedia.entity.Users;
import com.app.cosmomedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCRUDImp implements UserCRUD {

    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     * @param pageable Pagination information.
     * @return Users data
     */
    @Override
    public Page<Users> getUsersList(Pageable pageable) {
        return userRepository.findByDeletedAtNullOrderByCreatedAtDesc(pageable);
    }

    /**
     *
     * @param pageable Pagination information.
     * @return Deleted users data
     */
    @Override
    public Page<Users> getDeletedUsersList(Pageable pageable) {
        return userRepository.findByDeletedAtNotNullOrderByDeletedAtDesc(pageable);
    }

    /**
     * @param users User data to create a new user.
     * @return Message "Success"
     * @throws MessagingException for err
     */
    @Override
    public String addUser(Users users) throws MessagingException, IOException {
        try {
            if (userRepository.findByEmail(users.getEmail()).isEmpty()) {
                // Create MimeMessage and MimeMessageHelper for sending email
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                // Save user information to the database
                Users user = Users.builder()
                        .CIN(users.getCIN())
                        .firstName(users.getFirstName())
                        .lastName(users.getLastName())
                        .email(users.getEmail())
                        .picture(users.getPicture())
                        .role(users.getRole())
                        .createdAt(new Date())
                        .modifiedAt(new Date())
                        .build();
                userRepository.save(user);

                // Set email details
                messageHelper.setFrom("anasfananibusiness@gmail.com");
                messageHelper.setTo(user.getEmail());
                messageHelper.setSubject("Your Sokker Login Credentials");

                // Load email content from the HTML file in the resources/static directory
                ClassPathResource resource = new ClassPathResource("static/email.html");
                InputStream inputStream = resource.getInputStream();
                byte[] emailContentBytes = StreamUtils.copyToByteArray(inputStream);
                String emailContent = new String(emailContentBytes, StandardCharsets.UTF_8);

                // Set the HTML content
                messageHelper.setText(emailContent, true);

                javaMailSender.send(mimeMessage);

                return "User added successfully, and welcome message sent.";
            } else {
                return "Email already used";
            }
        } catch (Exception e) {
            // Handle exceptions appropriately, log or rethrow as needed
            throw e;
        }
    }

    /**
     * @param updatedUser The user entity with updated information.
     * @return string
     */
    @Override
    public String updateUser(Users updatedUser) {
        try {
            // Check if the user with the given email exists
            Optional<Users> existingUserOptional = userRepository.findByEmail(updatedUser.getEmail());
            if (existingUserOptional.isPresent()) {
                Users existingUser = existingUserOptional.get();

                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                // Check if the current user is an ADMIN or the owner of the account
                if (principal instanceof Users) {
                    Users currentUser = (Users) principal;

                    // Check if the current user is an ADMIN or the owner of the account
                    if (currentUser.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
                        // Update all fields
                        existingUser.setFirstName(updatedUser.getFirstName());
                        existingUser.setEmail(updatedUser.getEmail());
                        existingUser.setLastName(updatedUser.getLastName());
                        existingUser.setPicture(updatedUser.getPicture());
                        existingUser.setCIN(updatedUser.getCIN());
                        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
                        existingUser.setRole(updatedUser.getRole());

                        // Update the modifiedAt field
                        existingUser.setModifiedAt(new Date());

                        // Save the updated user to the database
                        userRepository.save(existingUser);

                        return "User updated successfully all.";
                    } else {
                        // Update pic and phone fields
                        existingUser.setPicture(updatedUser.getPicture());
                        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());

                        // Update the modifiedAt field
                        existingUser.setModifiedAt(new Date());

                        // Save the updated user to the database
                        userRepository.save(existingUser);
                        return "User updated successfully except email , role and cin.";
                    }
                } else {
                    return "Unauthorized: Only authenticated users can perform this operation.";
                }
            } else {
                return "User not found for the provided email.";
            }
        } catch (Exception e) {
            // Handle exceptions appropriately, log or rethrow as needed
            throw e;
        }
    }

    /**
     *
     * @param CIN The Customer Identification Number of the user to be soft-deleted.
     * @return String of success or err
     */
    @Override
    public String softDeleteUser(String CIN) {
        Optional<Users> existingUserOptional = userRepository.findByCIN(CIN);
        try {
            if (existingUserOptional.isPresent()) {
                Users existingUser = existingUserOptional.get();

                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                // Check if the current user is an ADMIN or the owner of the account
                if (principal instanceof Users) {
                    Users currentUser = (Users) principal;

                    // Check if the current user is an ADMIN or the owner of the account
                    if (currentUser.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
                        existingUser.setDeletedAt(new Date());
                        existingUser.setDeletedBy(currentUser.getFirstName() + " " + currentUser.getLastName());
                        userRepository.save(existingUser);
                        return "User deleted successfully";
                    }else {
                        return "Error in deleting the user";
                    }
                } else {
                    return "Unauthorized: Only authenticated users can perform this operation.";
                }
            }else {
                return "User not found";
            }
        } catch (Exception e){
            throw e;
        }

    }

    @Override
    public Page<Users> filterUsers(String firstName, String lastName, Date startDate, Date endDate, String email, String role, String deletedBy) {
        return null;
    }

}
