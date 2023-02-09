package com.kindlebit.pos.controllers;


import com.kindlebit.pos.models.Customer;

import com.kindlebit.pos.service.CustomerService;
import com.kindlebit.pos.utill.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

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
    public Response editCustomer(@PathVariable(value = "id") Long id, @RequestBody Customer customer) {
        Customer editCustomer = customerService.editCustomer(id, customer);
        Response response = new Response();
        response.setMessage(" Customer has been updated . ");
        response.setStatusCode(200);
        response.setBody(editCustomer);
        return response;
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


}
