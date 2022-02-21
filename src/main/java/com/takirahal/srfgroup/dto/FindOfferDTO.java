package com.takirahal.srfgroup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindOfferDTO extends OfferDTO  {
    private Double amount;
}
