package com.example.jpabook.embedded;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter//SEtter는 아예 막는다
@NoArgsConstructor(access = AccessLevel.PROTECTED)//기본 생성자는 막아둔다.(그나마 안전)
@AllArgsConstructor //생성자에서 값을 모두 받는다 딱 생성시 1번만
public class Address {
    private String city;
    private String street;
    private String zipcode;

}
