package com.kindlebit.pos.service;

import com.kindlebit.pos.models.Customer;
import com.kindlebit.pos.models.TableTop;

import com.kindlebit.pos.utill.Response;

import java.util.List;
import java.util.Set;


public interface TableService {


    Response storeTable(TableTop tableTop);

    List<TableTop> tablesList();

    Response editTable(String tableName, TableTop tableTop);

    Boolean deleteTable(String tableName);

    TableTop bookTable(Integer capacity, Customer customer);


    List<TableTop> freeTablesList();

    List<TableTop> tableAccordingToType(String type);


    List<TableTop> freeAndBookedTableList();



}
