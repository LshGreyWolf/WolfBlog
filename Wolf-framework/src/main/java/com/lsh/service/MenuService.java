package com.lsh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2022-10-04 15:36:31
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long userId);


    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult MenuList(Menu menu);

    ResponseResult addMenu(Menu menu);
}

