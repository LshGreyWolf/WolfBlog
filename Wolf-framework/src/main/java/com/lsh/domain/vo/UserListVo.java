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
public class UserListVo {


    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("email")
    private String email;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("nickName")
    private String nickName;
    @JsonProperty("phonenumber")
    private String phonenumber;
    @JsonProperty("sex")
    private String sex;
    @JsonProperty("status")
    private String status;

    @JsonProperty("userName")
    private String userName;


    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
