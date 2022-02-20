package com.takirahal.srfgroup.dto;

import com.takirahal.srfgroup.entities.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String imageUrl;

    private String phone;

    private String sourceProvider;

    private String idOneSignal;

    private Set<Authority> authorities;
}
