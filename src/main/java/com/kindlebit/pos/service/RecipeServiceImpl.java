package com.kindlebit.pos.service;


import com.kindlebit.pos.models.Menu;
import com.kindlebit.pos.models.Recipe;
import com.kindlebit.pos.repository.MenuRepository;
import com.kindlebit.pos.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;


@Service
public class RecipeServiceImpl implements RecipeService {


    @Autowired
    MenuRepository menuRepository;

    @Autowired
    RecipeRepository recipeRepository;


    @Override
    public Recipe newRecipe(MultipartFile file, Recipe recipe,Long menuId) throws IOException {


        Optional<Menu> menu =menuRepository.findById(menuId);

        Recipe recipeDb=new Recipe();
        if(!menu.isPresent())
        {
            throw  new RuntimeException(" sorry menu is not found ");
        }else {

            recipeDb.setMenu(menu.get());
            recipeDb.setName(recipe.getName());
            recipeDb.setVeg(recipe.getVeg());
            recipeDb.setDescription(recipe.getDescription());
            recipeDb.setFullPrice(recipe.getFullPrice());
            recipeDb.setHalfPrice(recipe.getHalfPrice());
            recipeDb.setCreatedAt(new Date());
            recipeDb.setImageData(file.getBytes());

            recipeRepository.save(recipeDb);

        }
        return recipeDb;
    }
}
