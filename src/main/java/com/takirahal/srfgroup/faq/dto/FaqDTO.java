package com.takirahal.srfgroup.faq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaqDTO  implements Serializable {
    private Long id;

    private String questionAr;

    private String questionFr;

    private String questionEn;

    @Lob
    private String responseAr;

    @Lob
    private String responseFr;

    @Lob
    private String responseEn;
}
