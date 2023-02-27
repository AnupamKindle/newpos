package com.kindlebit.pos.controllers;



import com.kindlebit.pos.models.Orders;

import com.kindlebit.pos.service.OrderService;
import com.kindlebit.pos.utill.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {


@Autowired
OrderService orderService;

    @PostMapping("/place-order")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response placeOrder(@RequestBody Orders order, @RequestParam Long customerId) {
        Orders orderResponse=  orderService.placeOrder(order,customerId);
        Response response = new Response();
        response.setBody(orderResponse);
        response.setStatusCode(200);
        response.setMessage("your order has been created ");
        return response;
    }

    @GetMapping("/order-info/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response getOrder(@PathVariable Long id)
    {
        Orders orderResponse=  orderService.getOrder(id);
        Response response = new Response();
        response.setBody(orderResponse);
        response.setStatusCode(200);
        response.setMessage(" Information required order ");
        return response;

    }


    @PutMapping("/edit-order")
    @PreAuthorize("hasRole('ADMIN')")
    public Response editOrder(@RequestParam Long orderId,@RequestBody Orders order)
    {
        Orders orderResponse=  orderService.editOrder(order,orderId);
        Response response = new Response();
        response.setBody(orderResponse);
        response.setStatusCode(200);
        response.setMessage(" Order has been edited  ");
        return response;

    }





}
