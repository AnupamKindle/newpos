package com.kindlebit.pos.controllers;

import com.kindlebit.pos.service.ReportService;
import com.kindlebit.pos.utill.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reports")
public class ReportController {


    @Autowired
    ReportService reportService;

    @GetMapping("/loyal-customer")
    @PreAuthorize("hasRole('ADMIN')")
    public Response exportIntoExcelFile(@RequestParam(value ="fromDate", required=true) String toDate,@RequestParam(value ="toDate", required=true) String fromDate,@RequestParam Long noOfTime) throws ParseException{



        Response response = reportService.listOfLoyalCustomer(fromDate,toDate,noOfTime);


        return response;
    }





}
