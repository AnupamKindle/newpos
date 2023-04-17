package com.kindlebit.pos.service;


import com.kindlebit.pos.dto.MonthlyReportDTO;
import com.kindlebit.pos.dto.MonthlyReportDTOResponse;
import com.kindlebit.pos.dto.NumberOfTimeOrdersDTO;
import com.kindlebit.pos.models.Customer;
import com.kindlebit.pos.repository.CustomerRepository;
import com.kindlebit.pos.repository.OrdersRepository;

import com.kindlebit.pos.utill.NumberOfTimeOrdersDTOComparator;
import com.kindlebit.pos.utill.Response;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        // Code for excel Sheet


//creating an instance of Workbook class
        HSSFWorkbook workbook = new HSSFWorkbook();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String fileName="/home/kbs/Desktop/CustomersDetail-"+now+" .xlsx";

        FileOutputStream fileOut = new FileOutputStream(fileName);


//invoking creatSheet() method and passing the name of the sheet to be created
        HSSFSheet sheet = workbook.createSheet(" Loyal Customer List ");

        //creating the 0th row using the createRow() method
        Row rowhead = sheet.createRow((short)0);

        //creating cell by using the createCell() method and setting the values to the cell by using the setCellValue() method

        CellStyle cellStyle = workbook.createCellStyle();


        //set border to table
        cellStyle.setBorderTop(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.MEDIUM);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        // Header
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("Name");
        cell.setCellStyle(cellStyle);

        Cell cell1 = row.createCell(1);
        cell1.setCellValue("Phone Number");
        cell1.setCellStyle(cellStyle);


        Cell cell2 = row.createCell(2);
        cell2.setCellValue("Number Of Orders Time");
        cell2.setCellStyle(cellStyle);




        Integer rowCount=1;

        for(NumberOfTimeOrdersDTO numberOfTimeOrdersDTO: numberOfTimeOrdersDTOS)
        {
            Long id=numberOfTimeOrdersDTO.getCustomerId();
            Optional<Customer> customer=customerRepository.findById(id);


            if(customer.isPresent()) {
                Row row1 = sheet.createRow(rowCount++);
                row1.createCell(0).setCellValue(customer.get().getName());
                row1.createCell(1).setCellValue(customer.get().getPhoneNumber());
                if(numberOfTimeOrdersDTO.getTotalOrder()<2)
                {
                    CellStyle cellStyle2 = workbook.createCellStyle();
                    Cell cellTest = row1.createCell(2);
                    cellTest.setCellValue(numberOfTimeOrdersDTO.getTotalOrder());
                    cellStyle2.setBorderTop(BorderStyle.MEDIUM);
                    cellStyle2.setBorderRight(BorderStyle.MEDIUM);
                    cellStyle2.setBorderBottom(BorderStyle.MEDIUM);
                    cellStyle2.setBorderLeft(BorderStyle.MEDIUM);
                    cellStyle2.setAlignment(HorizontalAlignment.LEFT);
                    cellStyle2.setShrinkToFit(true);
                    cellStyle2.setFillForegroundColor(IndexedColors.RED.getIndex());
                    cellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cellTest.setCellStyle(cellStyle2);
                }else {
                    row1.createCell(2).setCellValue(numberOfTimeOrdersDTO.getTotalOrder());
                }

              //  System.out.println("========================================>>>>"+ numberOfTimeOrdersDTO.getTotalOrder());

            }

        }
        workbook.write(fileOut);

        //closing the Stream
        fileOut.close();
        //closing the workbook
        workbook.close();
         //prints the message on the console
        System.out.println("Excel file has been generated successfully.");

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
