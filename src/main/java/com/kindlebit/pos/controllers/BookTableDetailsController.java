package com.kindlebit.pos.controllers;


import com.kindlebit.pos.dto.BookTableDTO;
import com.kindlebit.pos.models.BookTableDetails;
import com.kindlebit.pos.models.TableTop;
import com.kindlebit.pos.service.BookTableDetailsService;
import com.kindlebit.pos.utill.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/booked-tables-detail")
public class BookTableDetailsController {

    @Autowired
    BookTableDetailsService bookTableDetailsService;


    @GetMapping("/booking-status")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response bookingStatus(@RequestParam String customerName, @RequestParam
      String bookedDate, @RequestParam String phoneNumber) throws ParseException {




        BookTableDTO bookTableDTO = bookTableDetailsService.bookTableStatus(customerName,bookedDate,phoneNumber);
        Response response=new Response();
        response.setMessage("Booking for  "+bookTableDTO.getCustomerName()+" on "  + bookedDate);
        response.setStatusCode(200);
        response.setBody(bookTableDTO);
        return response;
    }





}
