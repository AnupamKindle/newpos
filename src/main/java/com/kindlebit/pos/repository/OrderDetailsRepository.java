package com.kindlebit.pos.repository;


import com.kindlebit.pos.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {


    @Query("Select sum(od.totalAmount) from OrderDetails od where od.orderId=?1")
    Long totalAmount(Long orderId);




    @Query("Select od from OrderDetails od where od.orderId=?1")
    List<OrderDetails> listOfOrderItem(Long orderId);

}
