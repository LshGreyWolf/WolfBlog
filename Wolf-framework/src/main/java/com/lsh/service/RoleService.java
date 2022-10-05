package com.lsh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsh.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2022-10-04 15:41:19
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}

