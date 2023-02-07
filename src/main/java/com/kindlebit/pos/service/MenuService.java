package com.kindlebit.pos.service;


import com.kindlebit.pos.models.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuService {

    Optional<Menu> newMenu(Menu menu);

     Menu updateMenu(Long menuId,Menu menu);

    Boolean deleteMenu(Long menuId);

    Optional<Menu> getMenu(Long menuId);

    List<Menu> getAllMenu();

}
