package com.lsh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsh.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-01 08:40:31
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

