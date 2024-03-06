package com.app.cosmomedia.controller.authentication;

import com.app.cosmomedia.authentication.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    /**
     * Constructs an AuthenticationController with the specified AuthenticationService.
     *
     * @param service The AuthenticationService to be used for authentication and registration.
     */
    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    /**
     * Endpoint for user authentication.
     *
     * @param request The AuthenticationRequest containing user credentials.
     * @return ResponseEntity containing an AuthenticationResponse or an error message.
     */
    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authentication(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authentication(request));
    }

    /**
     * Endpoint for user registration.
     *
     * @param request The AuthenticationPassword containing user registration information.
     * @return ResponseEntity containing an AuthenticationResponse or an error message.
     */
    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationPassword request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
}
