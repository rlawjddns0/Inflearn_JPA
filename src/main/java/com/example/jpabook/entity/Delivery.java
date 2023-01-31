package com.example.jpabook.entity;

import com.example.jpabook.embedded.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Data
public class Delivery {
    @Id @GeneratedValue
    @Column(name="DELIVERY_ID")
    private Long id;


    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    @JsonIgnore
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)//무조건 String
    private DeliverStatus status;// READY,COMP

}
