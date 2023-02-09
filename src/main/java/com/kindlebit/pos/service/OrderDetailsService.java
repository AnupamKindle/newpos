package com.kindlebit.pos.service;


import com.kindlebit.pos.models.OrderDetails;
import com.kindlebit.pos.models.Orders;

public interface OrderDetailsService {


    OrderDetails createOrder(Long orderId, OrderDetails orderDetails);

}
