package com.app.cosmomedia.controller;

import com.app.cosmomedia.authentication.AuthenticationRequest;
import com.app.cosmomedia.authentication.AuthenticationResponse;
import com.app.cosmomedia.authentication.AuthenticationService;
import com.app.cosmomedia.authentication.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authentication(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authentication(request));
    }
}