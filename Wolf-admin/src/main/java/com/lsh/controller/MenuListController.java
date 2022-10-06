package com.lsh.controller;

import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.MenuListDto;
import com.lsh.domain.entity.Menu;
import com.lsh.service.MenuService;
import com.lsh.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/menu")
public class MenuListController {
    @Autowired
    private MenuService menuService;

    /**
     * 菜单列表
     * @param menuListDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult MenuList(MenuListDto menuListDto) {
        Menu menu = BeanCopyUtils.copyBean(menuListDto, Menu.class);
        return menuService.MenuList(menu);
    }

    @PostMapping
    public ResponseResult addMenu(Menu menu){
        return menuService.addMenu(menu);
    }
}
