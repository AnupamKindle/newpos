package com.kindlebit.pos.repository;

import com.kindlebit.pos.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT * FROM customer c WHERE c.name like CONCAT(?1, '%') and c.phone_number=?2", nativeQuery = true)
    Optional<Customer> findByNameAndPhoneNumber( String name,String phoneNumber);

    Optional<Customer> findByName(String name);

}
