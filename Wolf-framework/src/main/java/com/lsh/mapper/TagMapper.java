package com.lsh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsh.domain.dto.TagListDto;
import com.lsh.domain.entity.Tag;
import com.lsh.domain.vo.TagPageVo;
import org.apache.ibatis.annotations.Mapper;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-04 09:19:37
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    void addtag(TagListDto tagListDto);

    void updateTagById(TagPageVo tagPageVo);
}



