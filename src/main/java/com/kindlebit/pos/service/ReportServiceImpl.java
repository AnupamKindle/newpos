package com.kindlebit.pos.service;


import com.kindlebit.pos.dto.MonthlyReportDTO;
import com.kindlebit.pos.dto.MonthlyReportDTOResponse;
import com.kindlebit.pos.dto.NumberOfTimeOrdersDTO;
import com.kindlebit.pos.models.Customer;
import com.kindlebit.pos.repository.CustomerRepository;
import com.kindlebit.pos.repository.OrdersRepository;
import com.kindlebit.pos.utill.LoyalCustomerExcel;
import com.kindlebit.pos.utill.NumberOfTimeOrdersDTOComparator;
import com.kindlebit.pos.utill.Response;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.apache.poi.ss.util.CellUtil.createCell;


@Service
public class ReportServiceImpl implements ReportService{


@Autowired
OrdersRepository ordersRepository;


@Autowired
CustomerRepository customerRepository;





    @Override
    public Response listOfLoyalCustomer( String fromDate, String toDate, Long noOfTime) throws ParseException, IOException {


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

        return response ;
    }

    @Override
    public Response monthlySale(String year) {

        List<MonthlyReportDTO> monthlyReportDTOS=ordersRepository.monthlyReport();

        List<MonthlyReportDTOResponse> monthlyReportDTOResponseList=new ArrayList<MonthlyReportDTOResponse>() ;

        for(MonthlyReportDTO m: monthlyReportDTOS)
        {
            MonthlyReportDTOResponse monthlyReportDTOResponse=new MonthlyReportDTOResponse();

            if( (String.valueOf(m.getYear()).equals(year)))
            {

                monthlyReportDTOResponse.setYear(m.getYear());
                String monthName =  monthName(m.getMonth());
                monthlyReportDTOResponse.setMonthName(monthName);
                monthlyReportDTOResponse.setTotal_sale(m.getTotal_sale());
                monthlyReportDTOResponse.setNumberOfOrder(m.getNumberOfOrder());
                monthlyReportDTOResponseList.add(monthlyReportDTOResponse);
            }

        }
        Response response=new Response();
        response.setBody(monthlyReportDTOResponseList);
        response.setMessage("Monthly report of years");
        return response;
    }


    String monthName(Integer number)
    {
        String monthName = null;
        switch (number)
        {
            case 1:
               monthName="January";
               break;
            case 2:
                monthName="february";
                break;
            case 3:
                monthName="march";
                break;
            case 4:
                monthName="april";
                break;
            case 5:
                monthName="may";
                break;
            case 6:
                monthName="june";
                break;
            case 7:
                monthName="july";
                break;
            case 8:
                monthName="august";
                break;
            case 9:
                monthName="september";
                break;
            case 10:
                monthName="october";
                break;
            case 11:
                monthName="november";
                break;
            case 12:
                monthName="december";
                break;

        }

        return monthName;
    }


}
