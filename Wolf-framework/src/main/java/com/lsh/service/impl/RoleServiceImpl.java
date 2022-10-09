package com.lsh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.Role;
import com.lsh.domain.vo.PageVo;
import com.lsh.domain.vo.RoleVo;
import com.lsh.mapper.RoleMapper;
import com.lsh.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import com.lsh.service.RoleService;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2022-10-04 15:41:19
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> selectRoleKeyByUserId(Long userId) {
        //判断是否为管理员，返回集合中只需要有admin
        if (userId == 1L) {
            ArrayList<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //不是超级管理员，查询所对应的角色信息
        return roleMapper.selectRoleKeyByUserId(userId);

    }

    @Override
    public ResponseResult rolePage(Integer pageNum, Integer pageSize, Role role) {
        Page<Role> rolePage = new Page<>();
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(role.getRoleName()), Role::getRoleName, role.getRoleName());
        queryWrapper.like(Objects.nonNull(role.getStatus()), Role::getStatus, role.getStatus());
        queryWrapper.orderByAsc(Role::getRoleSort);

        Page<Role> page = roleMapper.selectPage(rolePage, queryWrapper);
        List<Role> records = page.getRecords();
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(records, RoleVo.class);
        PageVo pageVo = new PageVo();
        pageVo.setRows(roleVos);
        pageVo.setTotal(page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(Role role) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getId, role.getId());
        String status = role.getStatus();
//        queryWrapper.eq(Role::getStatus,role.getStatus());
        roleMapper.update(role, queryWrapper);

        return ResponseResult.okResult();
    }

    /**
     * 回显角色信息
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult getRole(Long id) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getId,id);
        List<Role> roles = roleMapper.selectList(queryWrapper);
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(roles, RoleVo.class);
        return ResponseResult.okResult(roleVos);
    }


}

