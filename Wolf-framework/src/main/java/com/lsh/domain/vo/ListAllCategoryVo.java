package com.lsh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListAllCategoryVo {

    private Long id;
    private String name ;
    private String description;
}
