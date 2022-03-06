package com.takirahal.srfgroup.offer.dto;

import com.takirahal.srfgroup.offer.dto.OfferDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindOfferDTO extends OfferDTO {
    private Double amount;
}
