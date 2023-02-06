package com.kindlebit.pos.repository;


import com.kindlebit.pos.models.Pantry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PantryRepository extends JpaRepository<Pantry,Long> {

    Optional<Pantry> findByItemName(String itemName);

}
