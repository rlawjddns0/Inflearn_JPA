package com.example.jpabook.service;

import com.example.jpabook.entity.Delevery;
import com.example.jpabook.entity.Member;
import com.example.jpabook.entity.Order;
import com.example.jpabook.entity.OrderItem;
import com.example.jpabook.entity.item.Item;
import com.example.jpabook.repository.ItemRepository;
import com.example.jpabook.repository.MemberRepository;
import com.example.jpabook.repository.OrderRepository;
import com.example.jpabook.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     **/
    @Transactional
    public Long Order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.find(memberId);
        Item item = itemRepository.findOne(itemId);

        Delevery delivery = new Delevery();
        delivery.setAddress(member.getAddress());


        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();

    }

    //주문 서비스의 주문과 주문 취소 메서드를 보면 비즈니스 로직 대부분이 엔티티에 있다. 서비스 계층
    //은 단순히 엔티티에 필요한 요청을 위임하는 역할을 한다. 이처럼 엔티티가 비즈니스 로직을 가지고 객체 지
    //향의 특성을 적극 활용하는 것을 도메인 모델 패턴(http://martinfowler.com/eaaCatalog/
    //domainModel.html)이라 한다. 반대로 엔티티에는 비즈니스 로직이 거의 없고 서비스 계층에서 대부분
    //의 비즈니스 로직을 처리하는 것을 트랜잭션 스크립트 패턴(http://martinfowler.com/eaaCatalog/
    //transactionScript.html)이라 한다.
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }



}
