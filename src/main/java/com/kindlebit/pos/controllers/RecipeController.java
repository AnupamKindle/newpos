package com.kindlebit.pos.controllers;


import com.kindlebit.pos.models.Recipe;
import com.kindlebit.pos.models.TableTop;
import com.kindlebit.pos.service.RecipeService;
import com.kindlebit.pos.utill.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    @Autowired
    RecipeService recipeService;


    @PostMapping("/new-recipe")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response newRecipe(@RequestParam String name,@RequestParam Boolean veg,@RequestParam Integer halfPrice,
                              @RequestParam Integer fullPrice,@RequestParam String description,
                              @RequestParam Long menuId,@RequestParam MultipartFile file) throws IOException {

        Recipe recipe=new Recipe();

        recipe.setName(name);
        recipe.setVeg(veg);
        recipe.setDescription(description);
        recipe.setFullPrice(fullPrice);
        recipe.setHalfPrice(halfPrice);

        Recipe recipeResponse=recipeService.newRecipe(file,recipe,menuId);
        Response response = new Response();
        response.setBody(recipeResponse);
        response.setStatusCode(200);
        response.setMessage("table has been saved");
        return response;
    }










}
