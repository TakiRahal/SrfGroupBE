package com.takirahal.srfgroup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellOfferDTO extends OfferDTO {
    private Double amount;
    private Instant sellDate;
}
