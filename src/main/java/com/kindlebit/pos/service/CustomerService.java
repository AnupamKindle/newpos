package com.kindlebit.pos.service;

import com.kindlebit.pos.models.Customer;
import com.kindlebit.pos.utill.Response;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer getCustomer(Long CustomerId);

    Response customerList(Integer pageNo, Integer pageSize, String sortBy, String sortDir,String searchByName,String searchByPhone);

    Response editCustomer(Long customerId, Customer customer);

    Boolean deleteCustomer(Long customerId);


    Response customerListByPagination(Integer pageNo, Integer pageSize, String sortBy, String sortDir,String searchByName,String searchByPhone);
}
