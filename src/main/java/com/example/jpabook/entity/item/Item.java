package com.example.jpabook.entity.item;

import com.example.jpabook.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//상속관계는 테이블 전략을 정해줘야 한다.(한테이블에 몽땅 다 떄려넣기)
@DiscriminatorColumn(name="dtype")//저장될떄 자식 클래스가 어떤 이름으로 저장될지
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name="ITEM_ID")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany
    private List<Category> categories = new ArrayList<>();
}
