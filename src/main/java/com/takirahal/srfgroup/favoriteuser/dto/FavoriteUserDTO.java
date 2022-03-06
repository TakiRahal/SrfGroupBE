package com.takirahal.srfgroup.favoriteuser.dto;

import com.takirahal.srfgroup.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteUserDTO implements Serializable {

    private Long id;

    private Instant favoriteDate;

    private UserDTO currentUser;

    private UserDTO favoriteUser;
}
