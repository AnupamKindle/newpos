package com.kindlebit.pos.service;


import com.kindlebit.pos.dto.InvoiceDTO;
import com.kindlebit.pos.models.OrderDetails;
import com.kindlebit.pos.models.Orders;

import java.util.List;

public interface OrderDetailsService {


    OrderDetails entryInOrder(Long orderId, OrderDetails orderDetails);

    InvoiceDTO invoice(Long orderId, Double discount);

    OrderDetails editEntryInOrder(Long orderDetailsId, OrderDetails orderDetails);

    List<OrderDetails> allEntryAccordingToOrderId(Long orderId);


    Boolean deleteItemPresentInCart(Long orderDetailsId);


}
