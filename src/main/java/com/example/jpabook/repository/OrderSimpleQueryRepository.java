package com.example.jpabook.repository;

import com.example.jpabook.api.OrderSimpleApiController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {
    private final EntityManager em;
    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                "select new com.example.jpabook.repository.OrderSimpleQueryDto(o.id, m.username, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join  o.member m" +
                        " join  o.delivery d", OrderSimpleQueryDto.class).getResultList();

    }
}
