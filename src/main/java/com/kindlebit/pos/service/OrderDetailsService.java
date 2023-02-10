package com.kindlebit.pos.service;


import com.kindlebit.pos.dto.InvoiceDTO;
import com.kindlebit.pos.models.OrderDetails;
import com.kindlebit.pos.models.Orders;

public interface OrderDetailsService {


    OrderDetails entryInOrder(Long orderId, OrderDetails orderDetails);

    InvoiceDTO invoice(Long orderId, Double discount);


}
