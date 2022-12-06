package com.example.jpabook.entity.item;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")//싱클테이블 전략시 저장 될 때 어떤 값으로 저장될지
@Data
public class Book  extends Item{
    private String author;
    private String isbn;
}
