package com.example.jpabook.service;

import com.example.jpabook.embedded.Address;
import com.example.jpabook.entity.Member;
import com.example.jpabook.entity.Order;
import com.example.jpabook.entity.OrderStatus;
import com.example.jpabook.entity.item.Book;
import com.example.jpabook.entity.item.Item;
import com.example.jpabook.exception.NotEnoughStockException;
import com.example.jpabook.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {


    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired
    OrderRepository orderRepository;


    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = getMember();

        Item book = getItem("코난", 100000, 10);


        int orderCnt=2;

        //when
        Long orderId = orderService.Order(member.getId(), book.getId(), orderCnt);


        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER,getOrder.getStatus());
        assertEquals("주문항 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격*수량이다", 100000 * orderCnt, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.",8,book.getStockQuantity());


    }

    private Book getItem(String bookName, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(bookName);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member getMember() {
        Member member = new Member();
        member.setUsername("KIM11");
        member.setAddress(new Address("서울","12312","1231231"));
        em.persist(member);
        return member;
    }

    @Test(expected = NotEnoughStockException.class)
    public void 재고수량초과테스트() throws Exception {
        //given
        Member member=getMember();
        Item item = getItem("시골 JPA", 1000, 10);
        //when
        int orderCnt=11;
        orderService.Order(member.getId(), item.getId(), orderCnt);


        //then
        fail("재고수량예외가 터져야 한다.");

    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member=getMember();
        Book item = getItem("asdfasdf", 10000, 10);
        int orderCnt=2;

        Long orderId = orderService.Order(member.getId(), item.getId(), orderCnt);
        //when
        orderService.cancelOrder(orderId);


        //then
        Order order = orderRepository.findOne(orderId);

        assertEquals("주문취소 상태가 CANCEL 이어야 한다..",OrderStatus.CANCEL,order.getStatus());
        assertEquals("주무취소 수량과 주문 수량이 같아야 한다.",10, item.getStockQuantity());


    }


}