package com.kindlebit.pos.repository;


import com.kindlebit.pos.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {

    List<Recipe> findAllByMenuId(Long menuId);

    Recipe findByName(String recipeName);


}
