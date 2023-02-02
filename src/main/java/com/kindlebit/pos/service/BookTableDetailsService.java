package com.kindlebit.pos.service;


import com.kindlebit.pos.dto.BookTableDTO;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public interface BookTableDetailsService {


    BookTableDTO bookTableStatus(String customerName, String bookedDate, String phoneNumber) throws ParseException;

}
