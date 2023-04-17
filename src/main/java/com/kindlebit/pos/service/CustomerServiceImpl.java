package com.kindlebit.pos.service;


import com.kindlebit.pos.models.Customer;
import com.kindlebit.pos.repository.CustomerRepository;
import com.kindlebit.pos.utill.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Response customerList(Integer pageNo, Integer pageSize, String sortBy, String sortDir,String searchByName,String searchByPhone) {

        //assertThat(NumberUtils.isParsable("22")).isTrue()

        Response response=new Response();
        if(searchByName != null)
        {
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();


            // create Pageable instance
            Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
            Page<Customer> customerList=customerRepository.findByCustomerName(searchByName,pageable);
            response.setStatusCode(200);

            response.setBody(customerList);
            response.setMessage("Customer list according to name");
            return response;
        }
        if(searchByPhone !=null)
        {
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();


            // create Pageable instance
            Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
            Page<Customer> customerList=customerRepository.findByCustomerPhone(searchByPhone,pageable);
            response.setStatusCode(200);
            response.setMessage("Customer list according to phone number");

            response.setBody(customerList);
            return response;
        }

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);



        Page<Customer> customerPagination = customerRepository.findAll(pageable);
       response.setBody(customerPagination);
        response.setMessage("Customer list according to pagination ");
        response.setStatusCode(200);
        return response;
    }

    @Override
    public Response editCustomer(Long customerId,Customer customer) {


        Response response = new Response();

        Optional<Customer> existCustomer = customerRepository.findById(customerId);

        Customer editCustomer=new Customer();
        if(!existCustomer.isPresent())
        {
            response.setBody(null);
            response.setMessage("Sorry Customer not found ");
            response.setStatusCode(404);
          return response;
        }
        else {

            Long id = existCustomer.get().getId();
            String phoneNumber = ( !(customer.getPhoneNumber() .isEmpty()) ? customer.getPhoneNumber():existCustomer.get().getPhoneNumber() ) ;



            Optional<Customer> customerPhone= customerRepository.findByPhoneNumber(phoneNumber);
            if(customerPhone.isPresent()) {
                if (customerPhone.get().getId() != customerId) {
                    //throw new RuntimeException(" This number is already saved in DB ");

                    response.setBody(null);
                    response.setMessage(" This number is already exist");
                    response.setStatusCode(403);
                    return response;

                }
            }

            String name = ( customer.getName() !=""?customer.getName():existCustomer.get().getName() );
            Date createdDate= existCustomer.get().getCreatedAt();
            Date updatedDate = new Date();
            editCustomer.setId(id);
            editCustomer.setName(name.toLowerCase());
            editCustomer.setUpdatedAt(updatedDate);
            editCustomer.setCreatedAt(createdDate);
            editCustomer.setPhoneNumber(phoneNumber);
            customerRepository.save(editCustomer);

            response.setBody(editCustomer);
            response.setMessage(" Customer has been updated  ");
            response.setStatusCode(200);
            return response;
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

    @Override
    public Response customerListByPagination(Integer pageNo, Integer pageSize, String sortBy, String sortDir, String searchByName, String searchByPhone) {

        Response response=new Response();
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);



        Page<Customer> customerPagination = customerRepository.findAll(pageable);
        response.setStatusCode(200);
        response.setMessage("list of customer according to pagination ");
        response.setBody(customerPagination);


        return response;
    }


}
