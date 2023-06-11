package com.code.AssJava5.service;

import com.code.AssJava5.entity.Account;
import com.code.AssJava5.entity.Order;

public interface OrderService {

    Order order(Account user,String address);
}
