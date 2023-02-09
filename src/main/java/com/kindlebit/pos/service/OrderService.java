package com.kindlebit.pos.service;


import com.kindlebit.pos.models.Orders;

public interface OrderService {

    public Orders placeOrder(Orders order, Long customerId);

    Orders getOrder(Long orderId);




}
