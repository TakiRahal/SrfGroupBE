package com.takirahal.srfgroup.modules.user.dto;

import com.takirahal.srfgroup.modules.address.dto.AddressDTO;
import com.takirahal.srfgroup.modules.user.entities.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
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

    private Instant registerDate;

    private String langKey;

    private boolean blockedByAdmin;

    private String linkProfileFacebook;

    private String imageUrl;

    private String phone;

    private String sourceRegister;

    private Set<Authority> authorities;

    private AddressDTO address;
}
