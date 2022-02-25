package com.takirahal.srfgroup.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("RentOffer")
public class RentOffer extends Offer  {
    private static final long serialVersionUID = 1L;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;
}
