package com.example.jpabook.repository;

import com.example.jpabook.entity.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item){
        if(item.getId()==null){// 저장 전까지 id값이 없을수 있다.
            em.persist(item);
        }else{
            em.merge(item);//업데이트라고 생각
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findAll(){
        return em.createQuery("select I from Item I", Item.class).getResultList();
    }
    

}
