package com.code.AssJava5.service.Impl;

import com.code.AssJava5.entity.*;
import com.code.AssJava5.repositories.OrderDetailRepo;
import com.code.AssJava5.repositories.OrderRepo;
import com.code.AssJava5.service.CartService;
import com.code.AssJava5.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    CartService cartService;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    OrderDetailRepo orderDetailRepo;

    @Override
    public Order order(Account user, String address) {

        Order order=new Order();
        order.setAccount(user);
        order.setAddress(address);
        order.setCreateDate(new Date());
        List<OrderDetail> list=new ArrayList<>();
        for (Item item:cartService.getItems()){
            OrderDetail orderDetail=new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setPrice(item.getPrice());
            orderDetail.setQuantity(item.getQty());
            Product product=new Product();
            product.setId(item.getId());
            product.setName(item.getName());
            orderDetail.setProduct(product);
            list.add(orderDetail);
            orderDetailRepo.save(orderDetail);
        }
        orderRepo.save(order);
        orderDetailRepo.saveAll(list);
        cartService.clear();
        return order;
    }
}
