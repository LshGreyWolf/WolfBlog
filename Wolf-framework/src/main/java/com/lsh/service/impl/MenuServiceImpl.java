package com.lsh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.Menu;
import com.lsh.domain.vo.MenuListVo;
import com.lsh.mapper.MenuMapper;
import com.lsh.utils.BeanCopyUtils;
import com.lsh.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lsh.service.MenuService;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-10-04 15:36:31
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<String> selectPermsByUserId(Long userId) {
        //根据用户id查询权限关键字 如果用户id为1，就是超级管理员返回所有的权限和菜单类型为C和F的菜单
        //为什么要把 1 单独拿出来查询，就是因为在sys_role_menu表中的 role = 1 没有对应的菜单
        //role = 1就是userId = 1
        if (userId == 1) {
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            //得到菜单类型为C和F的菜单信息
            queryWrapper.in(Menu::getMenuType, "C", "F");
            //得到所有的menu的信息，但是只需要perm这个字段 使用stream流
            List<Menu> menus = menuMapper.selectList(queryWrapper);
            return menus.stream().map(Menu::getPerms).collect(Collectors.toList());
        }
        //不是超级管理员，返回应具有的权限
        //需要涉及到多表查询，
        //先根据用户id查出用户的对应权限role  在根据role查询对应的菜单的perm 其中菜单的类型为C和F 逻辑删除为0的
        return menuMapper.selectPermsByUserId(userId);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        //判断是否为管理员
        List<Menu> menus = null;
        if (SecurityUtils.isAdmin()) {
            //如果是返回所哟有符合要求的Mean
            menus = menuMapper.listRouters();

        } else {
            //如果不是，返回对应的menu
            menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //构建tree  先找出第一层的菜单，然后找到他们的子菜单设置到children中
        List<Menu> menuTree = bulidMenuTree(menus, 0L);
        return menuTree;
    }


    /**
     * 构建树
     *
     * @param menus
     * @param parentId
     * @return
     */
    public List<Menu> bulidMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream().filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());

        return menuTree;

    }

    /**
     * 获取存入参数的 子Menu集合
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream().filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, menus)))
                .collect(Collectors.toList());

        return childrenList;
    }

    /**
     * 菜单列表
     *
     * @param menu
     * @return
     */
    @Override
    public ResponseResult MenuList(Menu menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(menu.getStatus()), Menu::getStatus, menu.getStatus());
        queryWrapper.orderByAsc(Menu::getParentId);
        queryWrapper.orderByAsc(Menu::getOrderNum);
        queryWrapper.like(Menu::getMenuName,"");
        List<Menu> menus = menuMapper.selectList(queryWrapper);
        List<MenuListVo> menuListVos = BeanCopyUtils.copyBeanList(menus, MenuListVo.class);
        return ResponseResult.okResult(menuListVos);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        menuMapper.insert(menu);
        return ResponseResult.okResult();
    }

}

