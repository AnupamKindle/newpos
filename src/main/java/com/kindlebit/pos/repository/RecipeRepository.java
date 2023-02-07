package com.kindlebit.pos.repository;


import com.kindlebit.pos.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {

}
