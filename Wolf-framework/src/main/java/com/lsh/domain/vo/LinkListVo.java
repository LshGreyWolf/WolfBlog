package com.lsh.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkListVo {
    @JsonProperty("address")
    private String address;
    @JsonProperty("description")
    private String description;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("logo")
    private String logo;
    @JsonProperty("name")
    private String name;
    private String status;
}

