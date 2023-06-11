package com.code.AssJava5.repositories;

import com.code.AssJava5.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Integer> {


}
