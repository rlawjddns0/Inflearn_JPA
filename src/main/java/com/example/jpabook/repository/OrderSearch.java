package com.example.jpabook.repository;

import com.example.jpabook.entity.OrderStatus;
import lombok.Data;

@Data
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
