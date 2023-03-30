package com.kindlebit.pos.utill;


import com.kindlebit.pos.dto.NumberOfTimeOrdersDTO;
import com.kindlebit.pos.models.Customer;
import com.kindlebit.pos.repository.CustomerRepository;
import com.kindlebit.pos.repository.OrdersRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.apache.poi.ss.util.CellUtil.createCell;

public class LoyalCustomerExcel {



    public LoyalCustomerExcel() {

    }

    @Autowired
    OrdersRepository ordersRepository;


    @Autowired
    CustomerRepository customerRepository;





public static ByteArrayInputStream loyalCustomerToExcel(List<Customer> loyalCustomerList) throws IOException
{

    String[] columns={"ID","NAME","PHONE NUMBER"};

    try
    {
        Workbook workbook1=new XSSFWorkbook();
        ByteArrayOutputStream out =new ByteArrayOutputStream();

        Sheet sheet1= workbook1.createSheet(" LOYAL CUSTOMER ");

        Font headerFont= workbook1.createFont();

        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        CellStyle headerCellStyle = workbook1.createCellStyle();

        headerCellStyle.setFont(headerFont);


        //Row for Header ----------------------------->>

        Row headerRow =sheet1.createRow(0);

        //Header

        for(int col=0;col<columns.length;col++)
        {
            Cell cell =headerRow.createCell(col);
            cell.setCellValue(columns[col]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowIdx =1;

        for(Customer customer:loyalCustomerList)
        {
            Row row = sheet1.createRow(rowIdx++);

            if (customer.getId() instanceof Long)
               // cell.setCellValue((Date) obj);
            row.createCell(0).setCellValue((Long)customer.getId());
            else if (customer.getName() instanceof String)
                row.createCell(1).setCellValue((String) customer.getName());
            else if (customer.getPhoneNumber() instanceof String)
                row.createCell(2).setCellValue((String)customer.getPhoneNumber());

           /* row.createCell(0).setCellValue(customer.getId());
            row.createCell(1).setCellValue(customer.getName());
            row.createCell(2).setCellValue(customer.getPhoneNumber());*/
        }
       workbook1.write(out);

        return  new ByteArrayInputStream(out.toByteArray());
    }
    catch (Exception e)
    {
e.printStackTrace();
    }
    return null;
}














}
