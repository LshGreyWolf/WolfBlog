package com.lsh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListDto {
    private Long id;
    private String name;
    private String status;
    private String description;
}
