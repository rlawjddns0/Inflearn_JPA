package com.example.jpabook.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name="orders")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    //연관관계 메서드
    public void setMember(Member member){
        this.member=member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delevery delivery){
        this.delevery=delivery;
        delevery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delevery delevery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelevery(delevery);
        for (OrderItem orderItem :orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /** 주문 취소 **/
    public void cancel(){
        if(delevery.getStatus()==DeliverStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem:orderItems) {
            orderItem.cancel();
        }
    }

    /*
    전체 주문가격 조회로직
     */
    public int getTotalPrice(){
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }

}
