package com.kindlebit.pos.service;

import com.kindlebit.pos.dto.RecipeDTO;
import com.kindlebit.pos.models.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecipeService {

    Recipe newRecipe(MultipartFile file,Recipe recipe,Long menuId) throws IOException;

    List<RecipeDTO> recipesByMenu(Long menuId,String search) throws Exception;

    Recipe recipe(Long recipeId) throws Exception;

    Boolean deleteRecipe(Long recipeId) throws  Exception;
    Recipe editRecipe(Recipe recipe,Long recipeId,Long menuUId);

    List<Recipe> allVegRecipeByMenuId(Long menuId);

    List<Recipe> searchRecipeByName(String recipeName,Long menuId);
}
