package com.lsh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.domain.entity.Tag;
import com.lsh.mapper.TagMapper;
import org.springframework.stereotype.Service;
import com.lsh.service.TagService;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-10-04 09:19:39
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

