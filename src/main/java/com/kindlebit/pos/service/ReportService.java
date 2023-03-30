package com.kindlebit.pos.service;



import com.kindlebit.pos.utill.Response;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;


public interface ReportService {


   Response listOfLoyalCustomer( String toDate, String fromDate, Long noOfTime) throws ParseException, IOException;

   Response monthlySale(String year);






}
