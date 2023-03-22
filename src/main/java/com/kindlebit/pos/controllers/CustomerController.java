package com.kindlebit.pos.controllers;


import com.kindlebit.pos.models.Customer;

import com.kindlebit.pos.models.Orders;
import com.kindlebit.pos.service.CustomerService;
import com.kindlebit.pos.service.OrderService;
import com.kindlebit.pos.utill.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    OrderService orderService;

    @PostMapping("/create-customer")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response createCustomer(@RequestBody Customer customer) {
        Customer customerResponse = customerService.createCustomer(customer);
        Response response = new Response();
        response.setBody(customerResponse);
        response.setStatusCode(200);
        response.setMessage(" A new customer has been created ");
        return response;
    }


    @GetMapping("/customers")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response customerList() {
        List<Customer> customerList = customerService.customerList();
        Response response = new Response();
        response.setMessage(" List of all customer.");
        response.setStatusCode(200);
        response.setBody(customerList);
        return response;
    }


    @PutMapping("/edit-customer/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editCustomer(@PathVariable(value = "id") Long id, @RequestBody Customer customer) {
        Response customerResponse = customerService.editCustomer(id, customer);

        return ResponseEntity
                .status(customerResponse.getStatusCode())
                .body(customerResponse);
    }


    @DeleteMapping("/delete-customer/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Response deleteCustomer(@PathVariable(value = "id") Long id) {
        Boolean deleteCustomer = customerService.deleteCustomer(id);
        Response response = new Response();
        response.setMessage(" Customer has been deleted . ");
        response.setStatusCode(200);
        response.setBody(deleteCustomer);
        return response;
    }


    @PostMapping("/create-customer-and-place-order")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response createCustomerAndPlaceOrder(@RequestBody Customer customer,@RequestParam(value ="tableName", required=false) String tableName,
                                                @RequestParam String orderType) {
      //  Customer customerResponse = customerService.createCustomer(customer);

        Orders ordersResponse = orderService.createCustomerAndPlaceOrder(customer,tableName,orderType);
        Response response = new Response();
        response.setBody(ordersResponse);
        response.setStatusCode(200);
        response.setMessage(" Customer has been created. ");
        return response;
    }



}
