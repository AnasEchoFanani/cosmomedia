package com.app.cosmomedia.dto;

import com.app.cosmomedia.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@Builder
public class UsersDTO {
    private String firstName;
    private String lastName;
    private String email;
    private byte[] picture;
    private String role;
}
