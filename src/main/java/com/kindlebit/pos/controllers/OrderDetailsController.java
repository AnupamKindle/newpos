package com.kindlebit.pos.controllers;


import com.kindlebit.pos.dto.InvoiceDTO;
import com.kindlebit.pos.models.OrderDetails;
import com.kindlebit.pos.models.Orders;
import com.kindlebit.pos.service.OrderDetailsService;
import com.kindlebit.pos.service.OrderService;
import com.kindlebit.pos.utill.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order-detail")
public class OrderDetailsController {


    @Autowired
    OrderDetailsService orderDetailsService;


    @PostMapping("/add-entry-to-order")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response addEntryToOrder(@RequestBody OrderDetails order, @RequestParam Long orderId) {


        OrderDetails orderDetailsResponse =  orderDetailsService.entryInOrder(orderId,order);


        Response response = new Response();
        response.setBody(orderDetailsResponse);
        response.setStatusCode(200);
        response.setMessage(" Entry has been added to order ");
        return response;
    }



    @GetMapping("/order-details-invoice")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response orderDetailsInvoice(@RequestParam Long orderId,@RequestParam Double discount)
    {

        InvoiceDTO invoiceDTOResponse = orderDetailsService.invoice(orderId,discount);
        Response response = new Response();
        response.setBody(invoiceDTOResponse);
        response.setStatusCode(200);
        response.setMessage(" Invoice  ");
        return response;

    }

    @PutMapping("/edit-order-details")
    @PreAuthorize("hasRole('ADMIN')")
    public Response editOrderEntryDetails(@RequestParam Long orderDetailsId,@RequestBody OrderDetails order)
    {
        OrderDetails editOrderDetails = orderDetailsService.editEntryInOrder(orderDetailsId,order);
        Response response = new Response();
        response.setBody(editOrderDetails);
        response.setStatusCode(200);
        response.setMessage(" order entry has been edited  ");
        return response;

    }

    @GetMapping("/list-of-entry-according-to-order-id")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response listOfEntriesAccordingToOrderId(@RequestParam Long orderId)
    {

        List<OrderDetails> orderDetailsList = orderDetailsService.allEntryAccordingToOrderId(orderId);
        Response response = new Response();
        response.setBody(orderDetailsList);
        response.setStatusCode(200);
        response.setMessage(" Entries according to order ");
        return response;

    }


    @DeleteMapping("/delete-item-present-in-order")
    @PreAuthorize("hasRole('ADMIN')")
    public Response deleteItemPresentInOrder(@RequestParam Long orderDetailId)
    {

    Boolean delete = orderDetailsService.deleteItemPresentInCart(orderDetailId);
        Response response = new Response();
        response.setBody(delete);
        response.setStatusCode(200);
        response.setMessage(" This item is deleted from order ");
        return response;
    }




}
