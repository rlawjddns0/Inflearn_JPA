package com.example.jpabook.controller;

import com.example.jpabook.entity.Member;
import com.example.jpabook.entity.Order;
import com.example.jpabook.entity.item.Item;
import com.example.jpabook.repository.OrderSearch;
import com.example.jpabook.service.ItemService;
import com.example.jpabook.service.MemberService;
import com.example.jpabook.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final ItemService itemService;
    private final MemberService memberService;
    private final OrderService orderService;


    @GetMapping("/order")
    public String order(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String createOrder(@RequestParam("memberId") Long memberId,
                              @RequestParam("itemId") Long itemId,
                              @RequestParam("count") int count){

        orderService.Order(memberId,itemId,count);

        return "redirect:/orders";

    }

    @GetMapping(value = "/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch
                                    orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }
}
