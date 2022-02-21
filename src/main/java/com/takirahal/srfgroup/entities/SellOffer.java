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
@DiscriminatorValue("SellOffer")
public class SellOffer extends Offer {

    @Column(name = "amount")
    private Double amount;

    @Column(name = "sell_date")
    private Instant sellDate;
}
