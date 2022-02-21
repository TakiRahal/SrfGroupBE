package com.takirahal.srfgroup.mapper;

import com.takirahal.srfgroup.dto.OfferDTO;
import com.takirahal.srfgroup.entities.FindOffer;
import com.takirahal.srfgroup.entities.Offer;
import com.takirahal.srfgroup.entities.RentOffer;
import com.takirahal.srfgroup.entities.SellOffer;
import com.takirahal.srfgroup.enums.TypeOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomOfferMapper {

//    @Autowired
//    RentOfferMapper rentOfferMapper;
//
//    @Autowired
//    FindOfferMapper findOfferMapper;
//
//    @Autowired
//    SellOfferMapper sellOfferMapper;
//
//    @Autowired
//    OfferMapper offerMapper;

    public OfferDTO toDto(Offer offer) {
        return null;
//        if (offer == null) {
//            return null;
//        }
//
//        if (offer.getTypeOffer().equalsIgnoreCase(TypeOffer.SellOffer.toString())) {
//            SellOffer sellOffer = (SellOffer) offer;
//            return sellOfferMapper.toDto(sellOffer);
//        } else if (offer.getTypeOffer().equalsIgnoreCase(TypeOffer.RentOffer.toString())) {
//            RentOffer rentOffer = (RentOffer) offer;
//            return rentOfferMapper.toDto(rentOffer);
//        } else if (offer.getTypeOffer().equalsIgnoreCase(TypeOffer.FindOffer.toString())) {
//            FindOffer findOffer = (FindOffer) offer;
//            return findOfferMapper.toDto(findOffer);
//        }
//
//        return offerMapper.toDto(offer);
    }
}
