package com.takirahal.srfgroup.user.dto;

import com.takirahal.srfgroup.user.entities.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO  implements Serializable {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String imageUrl;

    private String phone;

    private String sourceRegister;

    private String idOneSignal;

    private Set<Authority> authorities;
}
