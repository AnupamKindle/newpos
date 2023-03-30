package com.kindlebit.pos.controllers;

import com.kindlebit.pos.service.ReportService;
import com.kindlebit.pos.utill.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.text.ParseException;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reports")
public class ReportController {


    @Autowired
    ReportService reportService;

    @GetMapping("/loyal-customer")
    @PreAuthorize("hasRole('ADMIN')")
    public Response exportIntoExcelFile(@RequestParam(value ="fromDate", required=true) String toDate,
                                                                   @RequestParam(value ="toDate", required=true) String fromDate,
                                                                   @RequestParam(value ="noOfTime", required=true) Long noOfTime) throws ParseException, IOException {

        Response response = reportService.listOfLoyalCustomer(fromDate,toDate,noOfTime);


        return response;
    }



    @GetMapping("/monthly-sale")
    @PreAuthorize("hasRole('ADMIN')")
    public Response monthlySale(@RequestParam(value ="year", required=true) String year)  {

        Response response = reportService.monthlySale(year);


        return response;
    }







}
