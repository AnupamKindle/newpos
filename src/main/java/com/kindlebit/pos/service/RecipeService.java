package com.kindlebit.pos.service;

import com.kindlebit.pos.models.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RecipeService {

    Recipe newRecipe(MultipartFile file,Recipe recipe,Long menuId) throws IOException;
}
