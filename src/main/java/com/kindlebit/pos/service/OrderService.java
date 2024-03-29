package com.kindlebit.pos.service;


import com.kindlebit.pos.models.Customer;
import com.kindlebit.pos.models.Orders;

public interface OrderService {

    public Orders placeOrder(Orders order, Long customerId);

    Orders getOrder(Long orderId);

    Orders editOrder(Orders order,Long orderId);

    Orders createCustomerAndPlaceOrder(Customer customer,String tableName,String orderType);


}
