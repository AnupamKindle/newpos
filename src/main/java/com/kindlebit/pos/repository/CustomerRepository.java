package com.kindlebit.pos.repository;

import com.kindlebit.pos.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByPhoneNumber(String phoneNumber);

    Optional<Customer> findByName(String name);

}
