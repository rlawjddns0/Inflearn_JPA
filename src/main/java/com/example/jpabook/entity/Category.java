package com.example.jpabook.entity;

import com.example.jpabook.entity.item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id @GeneratedValue
    @JoinColumn(name="CATEGORY_ID")
    private Long id;


    private String name;

    @ManyToMany//현업에서는 사용x
    @JoinTable(name="CATEGORY_ITEM",
                joinColumns = @JoinColumn(name="CATEGORY_ID"),
                inverseJoinColumns = @JoinColumn(name="ITEM_ID"))// 중간에 테이블을 하나 생성해줘야 한다.
    private List<Item> items = new ArrayList<>();

    //모든 연관관계는 지연로딩으로 설정!
    //즉시로딩( EAGER )은 예측이 어렵고, 어떤 SQL이 실행될지 추적하기 어렵다. 특히 JPQL을 실행할 때 N+1
    //문제가 자주 발생한다.
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

}
