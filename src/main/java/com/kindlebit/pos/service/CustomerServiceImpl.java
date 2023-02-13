package com.kindlebit.pos.service;


import com.kindlebit.pos.models.Customer;
import com.kindlebit.pos.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        Customer customerDb=new Customer();
        customerDb.setCreatedAt(new Date());
        customerDb.setName(customer.getName().toLowerCase());
        customerDb.setPhoneNumber(customer.getPhoneNumber());
        customerRepository.save(customerDb);
        return customerDb;
    }

    @Override
    public Customer getCustomer(Long customerId) {
        Optional<Customer> existCustomer = customerRepository.findById(customerId);
        if(!existCustomer.isPresent())
        {
            throw  new RuntimeException(" Sorry Customer not found ");
        }
        return existCustomer.get();
    }

    @Override
    public List<Customer> customerList() {
        List<Customer> customerList=customerRepository.findAll();
        return customerList;}

    @Override
    public Customer editCustomer(Long customerId,Customer customer) {

        Optional<Customer> existCustomer = customerRepository.findById(customerId);

        Customer editCustomer=new Customer();
        if(!existCustomer.isPresent())
        {
            throw  new RuntimeException(" Sorry Customer not found ");
        }
        else {

            Long id = existCustomer.get().getId();
            String phoneNumber = ( customer.getPhoneNumber() !=" " ? customer.getPhoneNumber():existCustomer.get().getPhoneNumber() ) ;
            String name = ( customer.getName() !=" "?customer.getName():existCustomer.get().getName() );
            Date createdDate= existCustomer.get().getCreatedAt();
            Date updatedDate = new Date();
            editCustomer.setId(id);
            editCustomer.setName(name.toLowerCase());
            editCustomer.setUpdatedAt(updatedDate);
            editCustomer.setCreatedAt(createdDate);
            editCustomer.setPhoneNumber(phoneNumber);
            customerRepository.save(editCustomer);
        return  editCustomer;
        }}

    @Override
    public Boolean deleteCustomer(Long customerId) {
        Optional<Customer> existCustomer = customerRepository.findById(customerId);
        if(!existCustomer.isPresent())
        {
            throw  new RuntimeException(" Sorry Customer not found ");
        }else {
            customerRepository.delete(existCustomer.get());
            return true;
        }
    }


}
