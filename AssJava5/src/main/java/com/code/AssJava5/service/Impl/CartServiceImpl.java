package com.code.AssJava5.service.Impl;

import com.code.AssJava5.entity.Item;
import com.code.AssJava5.entity.Product;
import com.code.AssJava5.repositories.ProductRepo;
import com.code.AssJava5.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    ProductRepo productRepo;

    Map<Integer,Item> map=new HashMap<>();

    ArrayList<Item> list=new ArrayList<Item>();

    @Override
    public Item add(Integer id) {

        Item item=map.get(id);
        if(item==null){
            item =new Item();
            Product p=new Product();
            List<Product> list=productRepo.findAll();
            p=list.stream().filter(it->it.getId()==id).collect(Collectors.toList()).get(0);
            item.setId(p.getId());
            item.setName(p.getName());
            item.setImg(p.getImg());
            item.setPrice(p.getPrice());
            item.setQty(1);
            map.put(id,item);
        }else {
            item.setQty(item.getQty()+1);
        }

        return item;
    }

    @Override
    public void remove(Integer id) {
        map.remove(id);
    }

    @Override
    public Item update(Integer id, Integer qty) {
        Item item=map.get(id);
        item.setQty(qty);
        return item;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Collection<Item> getItems() {
        return map.values();
    }

    @Override
    public int getCount() {
        return map.values().stream().mapToInt(item->item.getQty()).sum();
    }

    @Override
    public double getAmount() {
        return map.values().stream().mapToDouble(item->item.getPrice()*item.getQty()).sum();
    }
}
