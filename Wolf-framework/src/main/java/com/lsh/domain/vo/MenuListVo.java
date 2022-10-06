package com.lsh.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuListVo {

    @JsonProperty("component")
    private String component;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("id")
    private String id;
    @JsonProperty("isFrame")
    private Integer isFrame;
    @JsonProperty("menuName")
    private String menuName;
    @JsonProperty("menuType")
    private String menuType;
    @JsonProperty("orderNum")
    private Integer orderNum;
    @JsonProperty("parentId")
    private String parentId;
    @JsonProperty("path")
    private String path;
    @JsonProperty("perms")
    private String perms;
    @JsonProperty("remark")
    private String remark;
    @JsonProperty("status")
    private String status;
    @JsonProperty("visible")
    private String visible;
}
