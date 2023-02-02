package com.kindlebit.pos.service;


import com.kindlebit.pos.models.BookTableDetails;
import com.kindlebit.pos.models.Customer;
import com.kindlebit.pos.models.TableTop;
import com.kindlebit.pos.repository.BookTableDetailsRepository;
import com.kindlebit.pos.repository.CustomerRepository;
import com.kindlebit.pos.repository.TableRepository;
import com.kindlebit.pos.utill.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TableServiceImpl implements TableService {


    @Autowired
    TableRepository tableRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookTableDetailsRepository bookTableDetailsRepository;


    @Override
    public TableTop storeTable(TableTop tableTop) {

        Optional<TableTop> tableTop1 = tableRepository.findByTableName(tableTop.getTableName());
        if (tableTop != null) {
            if (tableTop1.isPresent()) {
                throw new RuntimeException("This table name is already in Data Base.");
            }

            TableTop tableTopResponse = tableRepository.save(tableTop);
            Response response = new Response();

            response.setMessage("Table has been saved");
            response.setStatusCode(200);
            response.setBody(response);
            return tableTopResponse;

        } else {
            throw new RuntimeException(" Kindly fill the data ! ");
        }
    }

    @Override
    public List<TableTop> tablesList() {
        List<TableTop> tableTops = tableRepository.findAll();
        Collections.sort(tableTops);
        return tableTops;
    }

    @Override
    public TableTop editTable(String tableName, TableTop tableTop) {
        Optional<TableTop> existTable = tableRepository.findByTableName(tableName);
        TableTop editTable = new TableTop();
        if (!existTable.isPresent()) {

            throw new RuntimeException(" Table not found ");
        } else {

            Long id = existTable.get().getId();
            String name = (tableTop.getTableName() != " " ? tableTop.getTableName() : existTable.get().getTableName());
            Integer capacity = (tableTop.getCapacity() != 0 ? tableTop.getCapacity() : existTable.get().getCapacity());
            String status = (tableTop.getStatus() != null ? tableTop.getStatus() : existTable.get().getStatus());

            String type = (tableTop.getType() != null ? tableTop.getType() : existTable.get().getType());
            Date updateDate = new Date();
            Date createdAt = existTable.get().getCreatedAt();

            editTable.setId(id);
            editTable.setTableName(name);
            editTable.setType(type);
            editTable.setCapacity(capacity);
            editTable.setCreatedAt(createdAt);
            editTable.setUpdatedAt(updateDate);
            editTable.setStatus(status);

            tableRepository.save(editTable);
        }
        return editTable;
    }

    @Override
    public Boolean deleteTable(String tableName) {

        Optional<TableTop> existTable = tableRepository.findByTableName(tableName);

        if (!existTable.isPresent()) {

            throw new RuntimeException(" Table not found ");
        } else {
            tableRepository.delete(existTable.get());
        }
        return null;
    }

    @Override
    public TableTop bookTable(Integer capacity, Customer customer) {

        List<TableTop> tableTops = tableRepository.findAllFreeTable();

        tableTops.stream().forEach(System.out::println);

        BookTableDetails bookTableDetails = new BookTableDetails();

        HashSet<TableTop> tableTopSet = (HashSet<TableTop>) tableTops.stream().filter(t -> t.getCapacity() >= capacity).collect(Collectors.toSet());
        TreeSet<TableTop> tableTopTreeSet = new TreeSet<>();
        tableTopTreeSet.addAll(tableTopSet);

        //tableTopTreeSet.stream().forEach(System.out::println);

        if (tableTopTreeSet.isEmpty()) {
            throw new RuntimeException("All tables are occupied !!");
        }
        TableTop freeTable = tableTopTreeSet.stream().findFirst().get();

        freeTable.setStatus("booked");

        tableRepository.save(freeTable);
        Optional<Customer> existCustomer = customerRepository.findByPhoneNumber(customer.getPhoneNumber());
        if (existCustomer.isPresent()) {

            customerRepository.save(existCustomer.get());

            bookTableDetails.setTableId(freeTable.getId());
            bookTableDetails.setCustomerId(existCustomer.get().getId());

            bookTableDetails.setBookedDate(new Date());

            bookTableDetailsRepository.save(bookTableDetails);

        } else {
            customer.setCreatedAt(new Date());
            Customer customer1 = customerRepository.save(customer);

            bookTableDetails.setTableId(freeTable.getId());
            bookTableDetails.setCustomerId(customer1.getId());
            bookTableDetails.setBookedDate(new Date());
            bookTableDetailsRepository.save(bookTableDetails);

        }


        return freeTable;
    }


    // Adding comment to merge in master

}
