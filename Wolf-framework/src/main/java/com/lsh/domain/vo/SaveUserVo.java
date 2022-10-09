package com.lsh.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserVo {

    private String email;

    private Long id;

    private String nickName;

    private String sex;

    private String status;

    private String userName;
    //用户权限关联表的数据
    private List<Long> roleIds;
}
