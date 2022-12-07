package com.example.jpabook.entity;

import com.example.jpabook.embedded.Address;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Data
public class Delevery {
    @Id @GeneratedValue
    @Column(name="DELEVERY_ID")
    private Long id;


    @OneToOne(mappedBy = "delevery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)//무조건 String
    private DeliverStatus status;// READY,COMP

}
