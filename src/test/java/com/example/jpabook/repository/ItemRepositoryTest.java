package com.example.jpabook.repository;

import com.example.jpabook.entity.item.Book;
import com.example.jpabook.entity.item.Item;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @Transactional
    public void saveItemTest() throws Exception {
        //given
        Book book = new Book();
        book.setAuthor("KIM");
        book.setPrice(1000);



        //when
        itemRepository.save(book);

        //then
        Item findItem = itemRepository.findOne(book.getId());
        Assert.assertEquals(findItem,book);


    }

}