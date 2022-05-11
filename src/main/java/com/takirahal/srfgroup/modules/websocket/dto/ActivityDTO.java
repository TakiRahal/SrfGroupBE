package com.takirahal.srfgroup.modules.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDTO {
    private String sessionId;

    private String userLogin;

    private String ipAddress;

    private String page;

    private Instant time;
}
