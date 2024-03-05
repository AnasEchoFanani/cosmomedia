package com.app.cosmomedia.controller;

import com.app.cosmomedia.authentication.AuthenticationRequest;
import com.app.cosmomedia.authentication.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class Test {
    @PostMapping
    public ResponseEntity<String> authentication(
    ) {
        return ResponseEntity.ok("test");
    }
}
