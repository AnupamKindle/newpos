package com.kindlebit.pos.repository;

import com.kindlebit.pos.models.BookTableDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface BookTableDetailsRepository extends JpaRepository<BookTableDetails,Long> {

    @Query(value = "SELECT btd FROM BookTableDetails btd WHERE btd.customerId =?1 and btd.bookedDate like CONCAT(?2, '%')  ")
    Optional<BookTableDetails> findByCustomerIdAndBookedDate(Long customerId, String bookedDate);

}
