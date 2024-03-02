package com.app.cosmomedia.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsersDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private byte[] picture;
    private String role;
}
