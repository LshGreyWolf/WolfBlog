package com.lsh.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddArticleDto {

    @JsonProperty("title")
    private String title;
    @JsonProperty("thumbnail")
    private String thumbnail;
    @JsonProperty("isTop")
    private String isTop;
    @JsonProperty("isComment")
    private String isComment;
    @JsonProperty("content")
    private String content;
    @JsonProperty("tags")
    private List<Long> tags;
    @JsonProperty("categoryId")
    private Integer categoryId;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("status")
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
}
