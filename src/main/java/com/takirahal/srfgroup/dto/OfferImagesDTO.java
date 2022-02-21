package com.takirahal.srfgroup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferImagesDTO {
    private Long id;

    private String path;

    private Instant dateCreated;

    private OfferDTO offer;

    private UserDTO user;
}
