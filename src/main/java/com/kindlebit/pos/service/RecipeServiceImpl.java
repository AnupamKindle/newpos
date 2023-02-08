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
import java.util.List;
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

    @Override
    public List<Recipe> recipesByMenu(Long menuId) throws Exception {
        if (menuId == 0 && menuId == null) {
            throw new RuntimeException(" Invalid Menu Id ");
        } else {
            List<Recipe> recipeList = recipeRepository.findAllByMenuId(menuId);
            return recipeList;}}

    @Override
    public Recipe recipe(Long recipeId) throws Exception {

        if(recipeId==0 && recipeId==null)
        {
            throw new RuntimeException(" Invalid Recipe Id ");
        }
        else {

            Optional<Recipe> recipe=recipeRepository.findById(recipeId);
            return recipe.get();
        }

    }

    @Override
    public Boolean deleteRecipe(Long recipeId) throws Exception {

        Optional<Recipe> recipe =recipeRepository.findById(recipeId);

        if(!recipe.isPresent())
        {
            throw new RuntimeException(" Invalid recipeId ");
        }
        else {
            recipeRepository.delete(recipe.get());
            return true;
        }
    }

    @Override
    public Recipe editRecipe(Recipe recipe, Long recipeId,Long menuId) {
        Optional<Recipe> existRecipe= recipeRepository.findById(recipeId);
         Recipe updatedRecipe = new Recipe();
         if(menuId !=null && menuId!=0) {
             Optional<Menu> menu = menuRepository.findById(menuId);
         recipe.setMenu(menu.get());
         }
        if(!existRecipe.isPresent())
        {
            throw new RuntimeException(" Recipe not found");
        }
        else {
            Long id = existRecipe.get().getId();
            String name =( (recipe.getName() !=" " && recipe.getName()!=null) ?recipe.getName():existRecipe.get().getName());
            Menu menu = (recipe.getMenu() !=null ? recipe.getMenu():existRecipe.get().getMenu());
            Boolean veg = (recipe.getVeg() !=null ? recipe.getVeg() :existRecipe.get().getVeg() );
            Integer halfPrice =( (recipe.getHalfPrice() !=null && recipe.getHalfPrice()!=0) ? recipe.getHalfPrice() : existRecipe.get().getHalfPrice());
            Integer fullPrice = ( (recipe.getFullPrice() !=null && recipe.getFullPrice()!=0) ? recipe.getFullPrice() : existRecipe.get().getFullPrice());
            byte[] imageData=(recipe.getImageData() !=null ? recipe.getImageData():existRecipe.get().getImageData());
            String description = ((recipe.getDescription() != null && recipe.getDescription() !="") ?recipe.getDescription():existRecipe.get().getDescription());
            Date createdDate = existRecipe.get().getCreatedAt();
            Date updatedDate = new Date();
            updatedRecipe.setId(id);
            updatedRecipe.setMenu(menu);
            updatedRecipe.setUpdatedAt(updatedDate);
            updatedRecipe.setImageData(imageData);
            updatedRecipe.setVeg(veg);
            updatedRecipe.setCreatedAt(createdDate);
            updatedRecipe.setHalfPrice(halfPrice);
            updatedRecipe.setFullPrice(fullPrice);
            updatedRecipe.setName(name);
            updatedRecipe.setDescription(description);
            Recipe recipeResponse = recipeRepository.save(updatedRecipe);
            return recipeResponse;
        }

    }


}
