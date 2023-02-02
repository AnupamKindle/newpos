package com.kindlebit.pos.service;

import com.kindlebit.pos.dto.BookTableDTO;
import com.kindlebit.pos.models.BookTableDetails;
import com.kindlebit.pos.models.Customer;
import com.kindlebit.pos.models.TableTop;
import com.kindlebit.pos.repository.BookTableDetailsRepository;
import com.kindlebit.pos.repository.CustomerRepository;
import com.kindlebit.pos.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class BookTableDetailsServiceImpl implements BookTableDetailsService{

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TableRepository tableRepository;

    @Autowired
    BookTableDetailsRepository bookTableDetailsRepository;

    @Override
    public BookTableDTO bookTableStatus(String customerName, String bookedDate, String phoneNumber) throws ParseException {


        System.out.println(customerName+" ====================>>>" + phoneNumber);
        Optional<Customer> customer= customerRepository.findByPhoneNumber(phoneNumber);



        if(!customer.isPresent())
        {
            throw new RuntimeException(" Sorry no booking for this customer .");
        }
        Long customerId= customer.get().getId();

        System.out.println("=================>>>>>>"+customerId);

        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(bookedDate);

        System.out.println("Date=================>>>>>>"+date1.toString());

        Optional<BookTableDetails> bookTableDetails =bookTableDetailsRepository.findByCustomerIdAndBookedDate(customerId,bookedDate);

        if(!bookTableDetails.isPresent())
        {
            throw new RuntimeException(" Sorry no booking in given date. ");
        }
       Optional<TableTop> tableTop = tableRepository.findById(bookTableDetails.get().getTableId());

        BookTableDTO bookTableDTO=new BookTableDTO();

        bookTableDTO.setCustomerName(customer.get().getName());
        bookTableDTO.setTableName(tableTop.get().getTableName());

        return bookTableDTO;
    }
}
