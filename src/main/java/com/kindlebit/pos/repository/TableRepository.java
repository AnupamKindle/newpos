package com.kindlebit.pos.repository;

import com.kindlebit.pos.models.TableTop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TableRepository extends JpaRepository<TableTop,Long> {

    Optional<TableTop> findByTableName(String name);


    @Query( value = "SELECT t FROM TableTop t WHERE t.status like '%free%' ")
    List<TableTop> findAllFreeTable();


    @Query( value = "SELECT t FROM TableTop t WHERE t.type LIKE  %?1%  ")
    List<TableTop> ListOfTablesAccordingToType(String type);


}
