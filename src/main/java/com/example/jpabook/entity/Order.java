package com.example.jpabook.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name="orders")
@Data
public class Order {


    @Id @GeneratedValue
    @Column(name="ORDER_ID")
    private Long id;

    @ManyToOne(fetch = LAZY) //XToOne은 무조건 지연 로딩으로 설정
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();


    @OneToOne(fetch = LAZY)
    @JoinColumn(name="DELEVERY_ID")//ONE TO ONE 관계에서는 foreignkey를 둘 중 하나에 둬도 된다. 하지만 정책상 많이 사용하는곳에 둔다.
    private Delevery delevery;

    private LocalDateTime orderDate;//주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

}
