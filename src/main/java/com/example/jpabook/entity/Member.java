package com.example.jpabook.entity;

import com.example.jpabook.embedded.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    private String username;

    @Embedded
    private Address address;

    //컬렉션은 필드에서 바로 초기화 하는 것이 안전하다.
    //null 문제에서 안전하다
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
