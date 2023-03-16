package com.kindlebit.pos.controllers;


import com.kindlebit.pos.models.Customer;
import com.kindlebit.pos.models.TableTop;
import com.kindlebit.pos.service.TableService;
import com.kindlebit.pos.utill.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/table")
public class TableController {

    @Autowired
    TableService tableService;

    @PostMapping("/new-table")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response newTable(@RequestBody TableTop tableTop) {
        tableTop.setCreatedAt(new Date());
        TableTop tableTop1 = tableService.storeTable(tableTop);
        Response response = new Response();
        response.setBody(tableTop1);
        response.setStatusCode(200);
        response.setMessage("table has been saved");
        return response;
    }


    @GetMapping("/tables")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response tableList()
    {

        List<TableTop> tableTops=tableService.tablesList();
        Response response=new Response();
        response.setMessage("Status of all tables");
        response.setStatusCode(200);
        response.setBody(tableTops);
        return response;
    }



    @PutMapping("/edit-table/{tableName}")
    @PreAuthorize("hasRole('ADMIN')")
    public Response editTable(@PathVariable(value = "tableName") String tableName , @RequestBody TableTop tableTop )
    {

        TableTop tableTops=tableService.editTable(tableName,tableTop);

        Response response=new Response();
        response.setMessage(" Table has been updated . ");
        response.setStatusCode(200);
        response.setBody(tableTops);
        return response;

    }


    @DeleteMapping("/delete-table/{tableName}")
    @PreAuthorize("hasRole('ADMIN')")
    public Response deleteTable(@PathVariable(value = "tableName") String tableName  )
    {

        Boolean tableTops=tableService.deleteTable(tableName);

        Response response=new Response();
        response.setMessage(" Table has been deleted . ");
        response.setStatusCode(200);
        response.setBody(tableTops);
        return response;

    }




    @PostMapping("/book-table")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response bookTable(@RequestParam Integer capacity, @RequestBody Customer customer) {

        TableTop tableTop1 = tableService.bookTable(capacity,customer);
        Response response = new Response();
        response.setBody(tableTop1);
        response.setStatusCode(200);
        response.setMessage(" table has been booked ");
        return response;
    }


    @GetMapping("/free-tables")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response freeTableList()
    {

        List<TableTop> tableTops=tableService.freeTablesList();
        Response response=new Response();
        response.setMessage("list of all free tables");
        response.setStatusCode(200);
        response.setBody(tableTops);
        return response;
    }



    @GetMapping("/table-list-according-to-type")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response tableListAccordingToType(@RequestParam String type)
    {

        List<TableTop> tableTops=tableService.tableAccordingToType(type);
        Response response=new Response();
        response.setMessage(" list of tables according to "+type);
        response.setStatusCode(200);
        response.setBody(tableTops);
        return response;
    }


}
