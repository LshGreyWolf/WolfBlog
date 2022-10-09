package com.lsh.domain.vo;

import com.lsh.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserVo {
    List<Long> roleIds;
    List<GetUserRoleVo> roles;
    UserListVo user;

}
