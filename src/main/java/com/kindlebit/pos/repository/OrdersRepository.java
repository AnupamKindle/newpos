package com.kindlebit.pos.repository;

import com.kindlebit.pos.dto.NumberOfTimeOrdersDTO;
import com.kindlebit.pos.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Long> {



    @Query(" SELECT new  com.kindlebit.pos.dto.NumberOfTimeOrdersDTO( o.customerId, COUNT(o.customerId) as totalOrder )FROM Orders o where o.orderDate BETWEEN ?1 AND  ?2 GROUP BY o.customerId  HAVING count(o.customerId) >= ?3 ")
    List<NumberOfTimeOrdersDTO> listOfLoyalCustomer(Date fromDate,Date  toDate, Long noOfTime);





}
