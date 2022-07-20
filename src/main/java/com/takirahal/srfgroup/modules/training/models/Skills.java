package com.takirahal.srfgroup.modules.training.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Skills {
    @Id
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequence_name_customer", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;

    private String skillName;
    private String skillType;
}
