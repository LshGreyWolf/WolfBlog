package com.lsh.controller;

import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.LoginUser;
import com.lsh.domain.entity.Menu;
import com.lsh.domain.entity.User;
import com.lsh.domain.vo.AdminUserInfoVo;
import com.lsh.domain.vo.RoutersVo;
import com.lsh.domain.vo.UserInfoVo;
import com.lsh.enums.AppHttpCodeEnum;
import com.lsh.exception.SystemException;
import com.lsh.service.LoginService;
import com.lsh.service.MenuService;
import com.lsh.service.RoleService;
import com.lsh.utils.BeanCopyUtils;
import com.lsh.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class loginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {

        if (!StringUtils.hasText(user.getUserName())) {
            //提示，必须要传入用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    /**
     * 分配权限和角色
     *
     * @return
     */
    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo() {
        //获取当前登录用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(userId);
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(userId);
        //获取用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);

    }

    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters() {
        Long userId = SecurityUtils.getUserId();
        //查询menu，结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        RoutersVo routersVo = new RoutersVo();
        routersVo.setMenus(menus);
        //封装返回
        return ResponseResult.okResult(routersVo);
    }
}
