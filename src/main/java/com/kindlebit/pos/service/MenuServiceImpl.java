package com.kindlebit.pos.service;


import com.kindlebit.pos.models.Menu;
import com.kindlebit.pos.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {


    @Autowired
    MenuRepository menuRepository;

    @Override
    public Optional<Menu> newMenu(Menu menu) {
        Optional<Menu> existingMenu = menuRepository.findByType(menu.getType());
        if (existingMenu.isPresent()) {
            throw new RuntimeException(" This type of Menu is already exists ");
        } else {
            Menu newMenu = new Menu();
            newMenu.setType(menu.getType());
            newMenu.setCreatedAt(new Date());
            menuRepository.save(newMenu);
            return Optional.of(newMenu);
        }
    }

    @Override
    public Menu updateMenu(Long Id,Menu menu) {
        Menu updatedMenu = new Menu();
        Long menuId = Id;
        Optional<Menu> existMenu = menuRepository.findById(menuId);
        if (!existMenu.isPresent()) {
            throw new RuntimeException(" Menu not exist !! ");
        }
        String type = (menu.getType() != " " ? menu.getType() : existMenu.get().getType());
        updatedMenu.setId(menuId);
        updatedMenu.setType(type);
        updatedMenu.setUpdatedAt(new Date());
        updatedMenu.setCreatedAt(existMenu.get().getCreatedAt());
        menuRepository.save(updatedMenu);
        return updatedMenu;
    }

    @Override
    public Boolean deleteMenu(Long menuId) {
        Optional<Menu> existMenu = menuRepository.findById(menuId);
        if (!existMenu.isPresent()) {
            throw new RuntimeException(" Menu not exist !! ");
        } else {
            menuRepository.delete(existMenu.get());
            return true;
        }
    }

    @Override
    public Optional<Menu> getMenu(Long menuId) {
        Optional<Menu> existMenu = menuRepository.findById(menuId);
        if (!existMenu.isPresent()) {
            throw new RuntimeException(" Menu not exist !! ");
        }
        return existMenu;
    }

    @Override
    public List<Menu> getAllMenu() {
        List<Menu> menuList=menuRepository.findAll();
        return menuList;
    }
}
