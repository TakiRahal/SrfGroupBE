package com.takirahal.srfgroup.modules.offer.entities;

import com.takirahal.srfgroup.modules.offer.entities.Offer;
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

    @Column(name = "type_contact_client")
    private String typeContactClient;
}
