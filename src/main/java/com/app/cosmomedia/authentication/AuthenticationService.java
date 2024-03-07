package com.app.cosmomedia.authentication;

import com.app.cosmomedia.dto.UsersDTO;
import com.app.cosmomedia.entity.Users;
import com.app.cosmomedia.repository.UserRepository;
import com.app.cosmomedia.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;

import java.util.Optional;

/**
 * Service class for handling user authentication operations.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    /**
     * Registers a new user or updates the password for an existing user.
     *
     * @param request The authentication password request containing user information.
     * @return An {@link AuthenticationResponse} containing a JWT token upon successful registration or an error message otherwise.
     */
    public AuthenticationResponse register(AuthenticationPassword request) {
        Optional<Users> usersOptional = userRepository.findByEmail(request.getEmail());
        if (usersOptional.isPresent()) {
            Users user = usersOptional.get();
            if (user.getPassword() == null) {
                // User doesn't have a password, so set the password, save, and generate a token
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                repository.save(user);
            } else {
                // User already has a password, handle this case appropriately
                return AuthenticationResponse.builder()
                        .error("You have already changed the password. Please contact an administrator to reset your password.")
                        .build();
            }
        } else {
            // User is not present, handle this case (e.g., return an error response)
            return AuthenticationResponse.builder()
                    .error("User not found for the given email address.")
                    .build();
        }
    }

    /**
     * Authenticates a user based on the provided email and password.
     *
     * @param request The authentication request containing user credentials.
     * @return An {@link AuthenticationResponse} containing a JWT token and user information upon successful authentication,
     * or an error message if authentication fails.
     */
    public AuthenticationResponse authentication(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            var jwtToken = jwtService.generateToken(user);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .user(UsersDTO.builder()
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .email(user.getEmail())
                            .picture(user.getPicture())
                            .role(user.getRole())
                            .build())
                    .build();
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return AuthenticationResponse.builder()
                    .error("Invalid email or password")
                    .build();
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
            return AuthenticationResponse.builder()
                    .error("Internal server error")
                    .build();
        }
    }
}
