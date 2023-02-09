package com.kindlebit.pos.service;

import com.kindlebit.pos.models.Customer;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer getCustomer(Long CustomerId);

    List<Customer> customerList();

    Customer editCustomer(Long customerId,Customer customer);

    Boolean deleteCustomer(Long customerId);
}
