package com.takirahal.srfgroup.offer.dto;

import com.takirahal.srfgroup.offer.dto.OfferDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentOfferDTO extends OfferDTO {
    private Double amount;
    private Instant startDate;
    private Instant endDate;
}
