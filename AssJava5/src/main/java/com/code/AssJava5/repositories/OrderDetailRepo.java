package com.code.AssJava5.repositories;

import com.code.AssJava5.entity.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderDetailRepo extends JpaRepository<OrderDetail,Integer> {

    @Query("SELECT o FROM OrderDetail o WHERE o.order.id=?1")
    Page<OrderDetail> getAll(int orderid, Pageable pageable);

    @Query(value = "SELECT sum(o.price * o.quantity) FROM OrderDetails o", nativeQuery = true)
    String countSumOrder();
}
