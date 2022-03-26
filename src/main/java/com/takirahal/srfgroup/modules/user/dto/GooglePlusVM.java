package com.takirahal.srfgroup.modules.user.dto;

import com.takirahal.srfgroup.modules.user.models.ProfileObj;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GooglePlusVM {
    String Ba;
    String accessToken;
    String googleId;
    ProfileObj profileObj;
    String tokenId;
    String sourceProvider;
    String idOneSignal;
}
