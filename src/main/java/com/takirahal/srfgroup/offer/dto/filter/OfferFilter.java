package com.takirahal.srfgroup.offer.dto.filter;

import com.takirahal.srfgroup.dto.AddressDTO;
import com.takirahal.srfgroup.dto.OfferImagesDTO;
import com.takirahal.srfgroup.dto.UserDTO;
import com.takirahal.srfgroup.dto.filter.UserOfferFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferFilter {
    private Long id;

    private String title;

    @Lob
    private String description;

    private Instant dateCreated;

    private String typeOffer;

    private UserOfferFilter user;

    private AddressDTO address;
}
