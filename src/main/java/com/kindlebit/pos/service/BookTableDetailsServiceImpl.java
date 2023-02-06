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


    /***
     *
     * @param customerName
     * @param bookedDate
     * @param phoneNumber
     * @return
     * @throws ParseException
     *
     * This method is used for to know the status of book table either it is book or not.
     * With associated customer.
     */
    @Override
    public BookTableDTO bookTableStatus(String customerName, String bookedDate, String phoneNumber) throws ParseException {

        Optional<Customer> customer= customerRepository.findByPhoneNumber(phoneNumber);
        if(!customer.isPresent())
        {
            throw new RuntimeException(" Sorry no booking for this customer .");
        }
        Long customerId= customer.get().getId();

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
