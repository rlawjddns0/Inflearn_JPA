package com.example.jpabook.entity;

import com.example.jpabook.entity.item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id @GeneratedValue
    @Column(name="ORDER_ITEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item; //주문 상품

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;//주문

    private int orderPrice;//주문가격
    private int count;//주문수량

}
