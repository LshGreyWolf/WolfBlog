package com.lsh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.TagListDto;
import com.lsh.domain.entity.Tag;
import com.lsh.domain.vo.TagPageVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2022-10-04 09:19:38
 */
public interface TagService extends IService<Tag> {


    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(Tag tag);

    ResponseResult deleteTag(Long id);

    ResponseResult updateTag(Long id);

    ResponseResult saveTag(Tag tag);

    ResponseResult listAllTag();
}

