package com.takirahal.srfgroup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferDTO  implements Serializable {
    private Long id;

    private String title;

    @Lob
    private String description;

    private Instant dateCreated;

    private String typeOffer;

    private Set<OfferImagesDTO> offerImages = new HashSet<>();

    private UserDTO user;

    private AddressDTO address;

    private CategoryDTO category;
}
