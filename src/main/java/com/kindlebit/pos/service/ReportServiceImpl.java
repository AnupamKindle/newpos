package com.kindlebit.pos.service;


import com.kindlebit.pos.dto.NumberOfTimeOrdersDTO;
import com.kindlebit.pos.models.Customer;
import com.kindlebit.pos.repository.CustomerRepository;
import com.kindlebit.pos.repository.OrdersRepository;
import com.kindlebit.pos.utill.NumberOfTimeOrdersDTOComparator;
import com.kindlebit.pos.utill.Response;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;



@Service
public class ReportServiceImpl implements ReportService{


@Autowired
OrdersRepository ordersRepository;


@Autowired
CustomerRepository customerRepository;


    @Override
    public Response listOfLoyalCustomer(String fromDate,String toDate,Long noOfTime) throws ParseException, IOException {

        Response response=new Response();
        String sDate1=toDate;
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
        String sDate2=fromDate;
        Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(sDate2);

        List<Customer> customerList= new ArrayList<Customer>();

        List<NumberOfTimeOrdersDTO> numberOfTimeOrdersDTOS= ordersRepository.listOfLoyalCustomer(date1,date2,noOfTime);

      numberOfTimeOrdersDTOS.sort(new NumberOfTimeOrdersDTOComparator());

        for(NumberOfTimeOrdersDTO numberOfTimeOrdersDTO: numberOfTimeOrdersDTOS)
        {
            Long id=numberOfTimeOrdersDTO.getCustomerId();
            Optional<Customer> customer=customerRepository.findById(id);

            if(customer.isPresent()) {
                Customer customerResponse = new Customer();
                customerResponse.setCreatedAt(customer.get().getCreatedAt());
                customerResponse.setName(customer.get().getName());
                customerResponse.setId(customer.get().getId());
                customerResponse.setPhoneNumber(customer.get().getPhoneNumber());
                customerResponse.setUpdatedAt(customer.get().getUpdatedAt());
                customerList.add(customerResponse);
            }

        }



        response.setBody(customerList);
        response.setStatusCode(200);
        response.setMessage("This is the list of loyal customers ");
        return response;
    }
}
