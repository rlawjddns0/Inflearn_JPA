package com.example.jpabook.controller;

import com.example.jpabook.entity.item.Book;
import com.example.jpabook.entity.item.Item;
import com.example.jpabook.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;


    @GetMapping("/items/new")
    public String createItem(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm bookForm){
        Book book = new Book();
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());
        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";


    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Book one = (Book)itemService.findOne(itemId);

        BookForm bookForm=new BookForm();
        bookForm.setId(one.getId());
        bookForm.setAuthor(one.getAuthor());
        bookForm.setIsbn(one.getIsbn());
        bookForm.setName(one.getName());
        bookForm.setPrice(one.getPrice());

        model.addAttribute("form", bookForm);

        return "items/updateItemForm";


    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form, Model model){
        Book book=new Book();
        book.setName(form.getName());
        book.setId(form.getId());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setIsbn(form.getIsbn());
        book.setAuthor(form.getAuthor());

        itemService.saveItem(book);

        return "redirect:/items";
    }
}
