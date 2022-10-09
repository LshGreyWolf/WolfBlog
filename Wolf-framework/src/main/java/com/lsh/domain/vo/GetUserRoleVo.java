package com.lsh.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  GetUserRoleVo {

    @JsonProperty("createBy")
    private String createBy;
    @JsonProperty("createTime")
    private String createTime;
    @JsonProperty("delFlag")
    private String delFlag;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("remark")
    private String remark;
    @JsonProperty("roleKey")
    private String roleKey;
    @JsonProperty("roleName")
    private String roleName;
    @JsonProperty("roleSort")
    private Integer roleSort;
    @JsonProperty("status")
    private String status;
    @JsonProperty("updateBy")
    private String updateBy;
}
