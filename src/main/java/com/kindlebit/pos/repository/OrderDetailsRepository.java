package com.kindlebit.pos.repository;


import com.kindlebit.pos.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {

}
