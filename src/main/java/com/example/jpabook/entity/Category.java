package com.example.jpabook.entity;

import com.example.jpabook.entity.item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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


    @ManyToOne
    @JoinColumn(name="PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

}
