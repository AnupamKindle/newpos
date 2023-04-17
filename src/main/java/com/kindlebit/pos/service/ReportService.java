package com.kindlebit.pos.service;



import com.kindlebit.pos.utill.Response;
import java.io.IOException;
import java.text.ParseException;


public interface ReportService {


   Response listOfLoyalCustomer( String toDate, String fromDate, Long noOfTime) throws ParseException, IOException;

   Response monthlySale(String year);






}
