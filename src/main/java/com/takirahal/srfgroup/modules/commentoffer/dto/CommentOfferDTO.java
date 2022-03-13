package com.takirahal.srfgroup.modules.commentoffer.dto;

import com.takirahal.srfgroup.modules.offer.dto.OfferDTO;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentOfferDTO {
    private Long id;

    private Instant createdDate;

    @Lob
    private String content;

    private OfferDTO offer;

    private UserDTO user;
}
