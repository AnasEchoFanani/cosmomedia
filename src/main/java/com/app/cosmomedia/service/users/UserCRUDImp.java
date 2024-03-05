package com.app.cosmomedia.service.users;

import com.app.cosmomedia.entity.Users;
import com.app.cosmomedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserCRUDImp implements UserCRUD {

    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     *
     * @param pageable Pagination information.
     * @return Users data
     */
    @Override
    public Page<Users> getUsersList(Pageable pageable) {
        return userRepository.findByDeletedAtNullOrderByCreatedAtDesc(pageable);
    }

    /**
     *
     * @param users User data to create a new user.
     * @return Message "Success"
     * @throws MessagingException for err
     */
    @Override
    public String addUser(Users users ) throws MessagingException, IOException {
        try {
            if (userRepository.findByEmail(users.getEmail()).isEmpty()){
                // Create MimeMessage and MimeMessageHelper for sending email
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                // Save user information to the database
                Users user = Users.builder()
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
            }else {
                return "Email already used";
            }
        } catch (Exception e) {
            // Handle exceptions appropriately, log or rethrow as needed
            throw e;
        }
    }

    @Override
    public String updateUser(Users users) {
        return null;
    }

    @Override
    public String softDeleteUser(String CIN) {
        return null;
    }

    @Override
    public Page<Users> filterUsers(String firstName, String lastName, Date startDate, Date endDate, String email, String role, String deletedBy) {
        return null;
    }

}
