package com.takirahal.srfgroup.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOneSignalDTO implements Serializable {

    private Long id;
    private String idOneSignal;
    private UserDTO user;
}
