package com.example.jpabook.api;

import com.example.jpabook.entity.Order;
import com.example.jpabook.repository.OrderRepository;
import com.example.jpabook.repository.OrderSearch;
import com.example.jpabook.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * xToOne(ManyToOne, OneToOne) 관계 최적화
 * Order
 * Order -> Member
 * Order -> Delivery
 *
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;



    //엔티티를 그대로 노출 하고 있다 -> 잘못된 방법
    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for(Order order :all){
            order.getMember().getUsername();// Lazy 강제 초기화  getMember() 까지는 디비에 안가지만 멤버에 이름을 가져올때 getUserName()을 실행할때 디비에간다.
            order.getDelivery().getAddress();
        }
        return all;
    }

}
