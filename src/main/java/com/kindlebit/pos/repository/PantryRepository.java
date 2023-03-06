package com.kindlebit.pos.repository;


import com.kindlebit.pos.models.Pantry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;


public interface PantryRepository extends JpaRepository<Pantry,Long> {

    Optional<Pantry> findByItemName(String itemName);


    @Query(value = "SELECT p FROM Pantry p WHERE p.quantity<=8")
    List<Pantry> getAllLowQuantity();


    @Query(value = "SELECT p FROM Pantry p WHERE p.itemName like %?1% ")
    List<Pantry> searchByName(String search);

}
