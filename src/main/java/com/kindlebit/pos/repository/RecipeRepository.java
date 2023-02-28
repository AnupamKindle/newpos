package com.kindlebit.pos.repository;


import com.kindlebit.pos.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {

    List<Recipe> findAllByMenuId(Long menuId);

    Recipe findByName(String recipeName);



    @Query( value = "SELECT r FROM Recipe r WHERE r.name LIKE  %?1% ")
    List<Recipe> searchRecipeByLikeName(String name);




    @Query(value = "select * from recipe where menu_id=?1 and veg=1",nativeQuery = true)
    List<Recipe> allVegRecipesByMenuId(Long menuId);


}
