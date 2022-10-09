package com.lsh.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.ChangeRoleStatusDto;
import com.lsh.domain.entity.Role;
import com.lsh.domain.entity.User;
import com.lsh.domain.vo.RoleVo;
import com.lsh.domain.vo.UserListVo;
import com.lsh.service.RoleService;
import com.lsh.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult rolePage(Integer pageNum, Integer pageSize, Role role) {
        return roleService.rolePage(pageNum, pageSize, role);
    }

    /**
     * 根据前端传过来的数据更新角色状态
     *
     * @param changeRoleStatusDto
     * @return
     */
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto changeRoleStatusDto) {
        Role role = new Role();
        role.setId(changeRoleStatusDto.getRoleId());
        role.setStatus(changeRoleStatusDto.getStatus());
        return ResponseResult.okResult(roleService.updateById(role));

    }

    /**
     * 回显角色信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getRole(@PathVariable Long id) {

        return roleService.getRole(id);

    }
    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus,0);
        List<Role> list = roleService.list(queryWrapper);
        List<RoleVo> userListVos = BeanCopyUtils.copyBeanList(list, RoleVo.class);
        return ResponseResult.okResult(userListVos);

    }
}
