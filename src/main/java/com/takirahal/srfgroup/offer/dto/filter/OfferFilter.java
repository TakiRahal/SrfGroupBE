package com.takirahal.srfgroup.offer.dto.filter;

import com.takirahal.srfgroup.dto.AddressDTO;
import com.takirahal.srfgroup.user.dto.filter.UserOfferFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.time.Instant;

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
