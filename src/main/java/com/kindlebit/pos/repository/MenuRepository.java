package com.kindlebit.pos.repository;

import com.kindlebit.pos.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu,Long> {

    Optional<Menu> findByType(String type);

}
