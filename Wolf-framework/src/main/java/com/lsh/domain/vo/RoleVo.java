package com.lsh.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("roleKey")
    private String roleKey;
    @JsonProperty("roleName")
    private String roleName;
    @JsonProperty("roleSort")
    private Integer roleSort;
    @JsonProperty("status")
    private String status;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
