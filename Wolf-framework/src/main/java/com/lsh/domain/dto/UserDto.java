package com.lsh.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @JsonProperty("userName")
    private String userName;
    @JsonProperty("nickName")
    private String nickName;
    @JsonProperty("password")
    private String password;
    @JsonProperty("phonenumber")
    private String phonenumber;
    @JsonProperty("email")
    private String email;
    @JsonProperty("sex")
    private String sex;
    @JsonProperty("status")
    private String status;
    @JsonProperty("roleIds")
    private List<Long> roleIds;
}
