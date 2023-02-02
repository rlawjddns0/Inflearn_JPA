package com.example.jpabook.api;

import com.example.jpabook.embedded.Address;
import com.example.jpabook.entity.Order;
import com.example.jpabook.entity.OrderStatus;
import com.example.jpabook.repository.OrderRepository;
import com.example.jpabook.repository.OrderSearch;
import com.example.jpabook.repository.OrderSimpleQueryDto;
import com.example.jpabook.repository.OrderSimpleQueryRepository;
import com.example.jpabook.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    private final OrderSimpleQueryRepository orderSimpleQueryRepository;



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


    /*
    쿼리가 총 1 + N + N번 실행된다. (v1과 쿼리수 결과는 같다.)
    order 조회 1번(order 조회 결과 수가 N이 된다.)
    order -> member 지연 로딩 조회 N 번
    order -> delivery 지연 로딩 조회 N 번
    예) order의 결과가 4개면 최악의 경우 1 + 4 + 4번 실행된다.(최악의 경우)
    지연로딩은 영속성 컨텍스트에서 조회하므로, 이미 조회된 경우 쿼리를 생략한다
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){//전용 DTO로 바꿔서 보여줘야 한다.
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result=orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return  result;

    }

    /**
     * V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
     * - fetch join으로 쿼리 1번 호출
     * 참고: fetch join에 대한 자세한 내용은 JPA 기본편 참고(정말 중요함)
     */
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithMemberDeliver();
        List<SimpleOrderDto> result=orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return  result;

    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4(){
       return orderSimpleQueryRepository.findOrderDtos();

    }

    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order){
            orderId=order.getId();
            name=order.getMember().getUsername(); //LAZY 초기화
            orderDate=order.getOrderDate();
            orderStatus=order.getStatus();
            address=order.getDelivery().getAddress(); //LAZY 초기화
        }
    }



}
