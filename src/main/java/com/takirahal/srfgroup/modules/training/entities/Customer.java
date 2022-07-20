package com.takirahal.srfgroup.modules.training.entities;

import com.takirahal.srfgroup.modules.training.models.AddressTraining;
import com.takirahal.srfgroup.modules.training.models.Skills;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequence_name_customer", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;
    private String fullname;
    private String email;

    @ManyToOne
    private AddressTraining address;

    @OneToMany
    private List<Skills> skills;
}
