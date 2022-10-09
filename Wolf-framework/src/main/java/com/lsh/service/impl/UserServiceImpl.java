package com.lsh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.UserDto;
import com.lsh.domain.entity.ArticleTag;
import com.lsh.domain.entity.Role;
import com.lsh.domain.entity.User;
import com.lsh.domain.entity.UserRole;
import com.lsh.domain.vo.*;
import com.lsh.exception.SystemException;
import com.lsh.mapper.RoleMapper;
import com.lsh.mapper.UserMapper;
import com.lsh.mapper.UserRoleMapper;
import com.lsh.service.UserRoleService;
import com.lsh.service.UserService;
import com.lsh.utils.BeanCopyUtils;
import com.lsh.utils.SecurityUtils;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreInvocationAttribute;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

import static com.lsh.enums.AppHttpCodeEnum.*;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-10-02 19:29:00
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 查看个人中心
     *
     * @return
     */
    @Override
    public ResponseResult userInfo() {
        //查询当前员用户id
        Long userId = SecurityUtils.getUserId();
        //根据id查询用户信息
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getId, userId);
        User user = userService.getOne(qw);
        //封装成userInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);


        return ResponseResult.okResult(userInfoVo);
    }

    /**
     * 修改个人信息
     *
     * @param user
     * @return
     */
    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();

    }

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(USERNAME_NOT_EXIST);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(PASSWORD_NOT_EXIST);
        }
        if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(NICKNAME_NOT_EXIST);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(EMAIL_NOT_EXIST);
        }

        //对数据进行是否存在判断
        if (userNameExist(user.getUserName())) {
            throw new SystemException(USERNAME_EXIST);
        }
        if (userNameExist(user.getNickName())) {
            throw new SystemException(NICKNAME_EXIST);
        }
        //...

        //对密码加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        userService.save(user);
        return ResponseResult.okResult();
    }


    /**
     * 判断数据库中是否存在该用户名
     *
     * @param userName
     * @return
     */
    private boolean userNameExist(String userName) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        int count = userService.count(queryWrapper);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户的分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param user
     * @return
     */
    @Override
    public ResponseResult userPage(Integer pageNum, Integer pageSize, User user) {
        Page<User> userPage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(user.getUserName()), User::getUserName, user.getUserName());

        Page<User> page = userMapper.selectPage(userPage, queryWrapper);
        List<User> records = page.getRecords();
        List<UserListVo> userListVos = BeanCopyUtils.copyBeanList(records, UserListVo.class);
        PageVo pageVo = new PageVo();
        pageVo.setRows(userListVos);
        pageVo.setTotal(page.getTotal());


        return ResponseResult.okResult(pageVo);
    }

    /**
     * 新增角色
     */
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public ResponseResult addUser(UserDto userDto) {
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);

        List<UserRole> userRoles = userDto.getRoleIds().stream().map(roleIds -> new UserRole(user.getId(), roleIds))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteUser(Long id) {

        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(id != userId, User::getId, id);
        userMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    /**
     * 根据id回显用户信息
     *
     * @param id
     * @return
     */
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public ResponseResult getUser(Long id) {

        User user = userMapper.selectById(id);
        //封装userListVo
        UserListVo userListVo = BeanCopyUtils.copyBean(user, UserListVo.class);
        //封裝getUserRoleVos
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, id);
        UserRole userRole = userRoleMapper.selectOne(queryWrapper);
        Long roleId = userRole.getRoleId();
        LambdaQueryWrapper<Role> qw = new LambdaQueryWrapper<>();
        qw.eq(Role::getId, roleId);
        List<Role> roles = roleMapper.selectList(qw);
        List<GetUserRoleVo> getUserRoleVos = BeanCopyUtils.copyBeanList(roles, GetUserRoleVo.class);
        //封裝roleIds
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id);
        List<UserRole> roleList = userRoleService.list(wrapper);
        List<Long> roleIdList = roleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());


        //封裝 GetUserVo
        GetUserVo getUserVo = new GetUserVo(roleIdList,getUserRoleVos,userListVo);

        return ResponseResult.okResult(getUserVo);
    }



    /**
     * 保存用户信息
     * @return
     */
    @Override
    public ResponseResult saveUser(SaveUserVo saveUserVo) {
        User user = BeanCopyUtils.copyBean(saveUserVo, User.class);
        userService.save(user);
        List<UserRole> userRoles = saveUserVo.getRoleIds().stream()
                .map(roleIds -> new UserRole(user.getId(), roleIds)).collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);

        return ResponseResult.okResult();
    }


}

