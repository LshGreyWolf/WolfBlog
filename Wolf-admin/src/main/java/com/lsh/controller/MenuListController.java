package com.lsh.controller;

import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.MenuListDto;
import com.lsh.domain.entity.Menu;
import com.lsh.domain.vo.MenuListVo;
import com.lsh.service.MenuService;
import com.lsh.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuListController {
    @Autowired
    private MenuService menuService;

    /**
     * 菜单列表
     * @param menu
     * @return
     */
    @GetMapping("/list")
    public ResponseResult MenuList(Menu menu) {
        List<Menu> menus = menuService.selectMenuList(menu);
        List<MenuListVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuListVo.class);
        return ResponseResult.okResult(menuVos);
    }

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @PostMapping
    public ResponseResult addMenu(Menu menu){
        return menuService.addMenu(menu);
    }
    @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable Long menuId){

        return menuService.deleteMenu(menuId);
    }

}
