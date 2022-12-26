package com.example.jpabook.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberForm {

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;

    private String zipcode;
    private String street;
    private String city;
}
