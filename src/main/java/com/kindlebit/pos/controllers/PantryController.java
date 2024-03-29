package com.kindlebit.pos.controllers;


import com.kindlebit.pos.dto.QuantityDTO;
import com.kindlebit.pos.models.Pantry;
import com.kindlebit.pos.models.TableTop;
import com.kindlebit.pos.service.PantryService;
import com.kindlebit.pos.utill.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pantry")
public class PantryController {

    @Autowired
    PantryService pantryService;

@PostMapping("/add-new-item-in-pantry")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
public Response newItem(@RequestBody Pantry pantry) {

    Response response=pantryService.storeNewItem(pantry);
    return response;
}

@GetMapping("/fetch-item")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public  ResponseEntity<?> fetchItem(@RequestParam Long pantryId,@RequestParam Integer quantity)
{
    Response response=pantryService.fetchItem(pantryId,quantity);

    return ResponseEntity
            .status(response.getStatusCode())
            .body(response);

}

@GetMapping("/to-know-quantity")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response knowQuantity(@RequestParam String name)
{

    Integer presentQuantity=pantryService.toKnowQuantity(name);
    Response response = new Response();
    response.setBody(presentQuantity);
    response.setStatusCode(200);
    response.setMessage(" Quantity present for  "+name +" is  "+presentQuantity);
    return response;

}


    @GetMapping("/lit-of-low-quantity")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response lowQuantityItems()
    {
        Response response = new Response();
        try {

            List<QuantityDTO> quantityDTOS = pantryService.lessQuantityInPantry();
            response.setBody(quantityDTOS);
            response.setStatusCode(200);
            response.setMessage(" These are the items which are low in quantity pantry ");
            return response;
        }
        catch (Exception e)
        {
e.printStackTrace();
        }
        return response;
    }

    @PutMapping("/update-item")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editItem(@RequestParam Long itemId, @RequestBody Pantry pantry)
    {

        Response responsePantry=pantryService.updateItem(itemId,pantry);

        return ResponseEntity
                .status(responsePantry.getStatusCode())
                .body(responsePantry);

    }


    @DeleteMapping("/delete-item")
    @PreAuthorize("hasRole('ADMIN')")
    public Response deleteItem(@RequestParam Long itemId)
    {

        Boolean responseItem=pantryService.deleteItem(itemId);
        Response response = new Response();
        response.setBody(responseItem);
        response.setStatusCode(200);
        response.setMessage(" Item has been deleted from pantry");
        return response;

    }



    @GetMapping("/all-items")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public  Response allItems(@RequestParam(value ="search", required=false) String search)
    {

        List<Pantry> pantryList=pantryService.listOfAllItems(search);
        Response response = new Response();
        response.setBody(pantryList);
        response.setStatusCode(200);
        response.setMessage(" List of all items in pantry");
        return response;

    }



}
