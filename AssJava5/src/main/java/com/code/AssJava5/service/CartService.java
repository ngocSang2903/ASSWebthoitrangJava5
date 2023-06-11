package com.code.AssJava5.service;

import com.code.AssJava5.entity.Item;

import java.util.Collection;

public interface CartService {

    Item add(Integer id);

    void remove(Integer id);

    Item update(Integer id,Integer qty);

    void clear();

    Collection<Item> getItems();

    int getCount();

    double getAmount();
}
