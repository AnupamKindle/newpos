package com.kindlebit.pos.controllers;


import com.kindlebit.pos.dto.RecipeDTO;
import com.kindlebit.pos.models.Recipe;

import com.kindlebit.pos.service.RecipeService;
import com.kindlebit.pos.utill.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;


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
        recipe.setName(name.toLowerCase());
        recipe.setVeg(veg);
        recipe.setDescription(description);
        recipe.setFullPrice(fullPrice);
        recipe.setHalfPrice(halfPrice);
        Recipe recipeResponse=recipeService.newRecipe(file,recipe,menuId);
        Response response = new Response();
        response.setBody(recipeResponse);
        response.setStatusCode(200);
        response.setMessage("A new recipe has been added ");
        return response;
    }

    @GetMapping("/recipes-by-menu")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response RecipesByMenu(@RequestParam Long menuId,@RequestParam(value ="search", required=false) String search) throws Exception {

        List<RecipeDTO> recipeDTOS=recipeService.recipesByMenu(menuId,search);

        Response response = new Response();
        response.setBody(recipeDTOS);
        response.setStatusCode(200);
        response.setMessage(" Recipe list according to menu ");
        return response;

    }



    @GetMapping("/recipe-information")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
   public Response recipe(@RequestParam Long recipeId) throws Exception {

        Recipe recipeResponse = recipeService.recipe(recipeId);
        Response response = new Response();
        response.setBody(recipeResponse);
        response.setStatusCode(200);
        response.setMessage(" Recipe Information  ");
        return response;

    }


    @DeleteMapping("/delete-recipe")
    @PreAuthorize("hasRole('ADMIN')")
    public Response deleteRecipe(@RequestParam Long recipeId) throws Exception {

        Boolean recipeResponse= recipeService.deleteRecipe(recipeId);
        Response response = new Response();
        response.setBody(recipeResponse);
        response.setStatusCode(200);
        response.setMessage(" Recipe has been deleted ");
        return response;

    }




    @PutMapping("/edit-recipe")
    @PreAuthorize("hasRole('ADMIN')")
    public Response editRecipe(@RequestParam String name,@RequestParam Boolean veg,@RequestParam Integer halfPrice,
                               @RequestParam Integer fullPrice,@RequestParam String description,
                               @RequestParam Long menuId,@RequestParam(value ="file", required=false) MultipartFile file ,@RequestParam Long recipeId) throws Exception
    {


        Recipe recipe=new Recipe();
        if(file!=null) {
            recipe.setImageData(file.getBytes());
        }
        else {
            recipe.setImageData(null);
        }
        recipe.setName(name.toLowerCase());
        recipe.setVeg(veg);
        recipe.setDescription(description);
        recipe.setFullPrice(fullPrice);
        recipe.setHalfPrice(halfPrice);

        Recipe recipeResponse= recipeService.editRecipe(recipe,recipeId,menuId);
        Response response = new Response();
        response.setBody(recipeResponse);
        response.setStatusCode(200);
        response.setMessage(" Recipe has been updated ");
        return response;

    }



    @GetMapping("/all-veg-recipe-by-menu")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response allVegRecipeByMenu(@RequestParam Long menuId) throws Exception {

        List<Recipe> recipeResponse = recipeService.allVegRecipeByMenuId(menuId);
        Response response = new Response();
        response.setBody(recipeResponse);
        response.setStatusCode(200);
        response.setMessage(" All Veg recipe according to menu ");
        return response;

    }


    @GetMapping("/search-recipe")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response searchRecipe(@RequestParam String recipeName,@RequestParam Long menuId) throws Exception {

        List<Recipe> recipeResponse = recipeService.searchRecipeByName(recipeName,menuId);
        Response response = new Response();
        response.setBody(recipeResponse);
        response.setStatusCode(200);
        response.setMessage(" These all recipes according to name  ");
        return response;

    }





}
