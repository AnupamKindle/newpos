package com.kindlebit.pos.controllers;


import com.kindlebit.pos.models.Menu;
import com.kindlebit.pos.service.MenuService;
import com.kindlebit.pos.utill.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/menu")
public class MenuController {

@Autowired
MenuService menuService;


    @PostMapping("/new-menu")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response newMenu(@RequestBody Menu menu) {
        Menu responseMenu = menuService.newMenu(menu).get();
        Response response = new Response();
        response.setBody(responseMenu);
        response.setStatusCode(200);
        response.setMessage(" A new menu has been created ");
        return response;
    }


    @GetMapping("/menu-list")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response menuList()
    {
        List<Menu> menuList= menuService.getAllMenu();
        Response response=new Response();
        response.setMessage("List of menu");
        response.setStatusCode(200);
        response.setBody(menuList);
        return response;
    }

    @PutMapping("/edit-menu/{menuId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Response editMenu(@PathVariable(value = "menuId") Long menuId , @RequestBody Menu menu )
    {
        Menu responseMenu = menuService.updateMenu(menuId,menu);
        Response response=new Response();
        response.setMessage(" Menu has been updated ! ");
        response.setStatusCode(200);
        response.setBody(responseMenu);
        return response;

    }


    @DeleteMapping("/delete-menu/{menuId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Response deleteTable(@PathVariable(value = "menuId") Long menuId )
    {
        Boolean responseMenu = menuService.deleteMenu(menuId);
        Response response=new Response();
        response.setMessage(" Menu has been deleted . ");
        response.setStatusCode(200);
        response.setBody(responseMenu);
        return response;

    }

}
