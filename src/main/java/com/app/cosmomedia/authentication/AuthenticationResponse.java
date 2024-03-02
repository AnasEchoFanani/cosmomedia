package com.app.cosmomedia.authentication;

import com.app.cosmomedia.dto.UsersDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private UsersDTO user;
    private String error;
}