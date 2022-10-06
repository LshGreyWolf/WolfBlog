package com.lsh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.TagListDto;
import com.lsh.domain.entity.Tag;
import com.lsh.domain.vo.PageVo;
import com.lsh.domain.vo.TagPageVo;
import com.lsh.domain.vo.ListAllTagVo;
import com.lsh.mapper.TagMapper;
import com.lsh.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lsh.service.TagService;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-10-04 09:19:39
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        Page<Tag> tagPage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto);
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());
        Page<Tag> page = tagMapper.selectPage(tagPage, queryWrapper);
        List<Tag> records = page.getRecords();
        List<TagPageVo> tagPageVos = BeanCopyUtils.copyBeanList(records, TagPageVo.class);
        long total = page.getTotal();
        PageVo pageVo = new PageVo();
        pageVo.setRows(tagPageVos);
        pageVo.setTotal(total);

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(Tag tag) {
        tagMapper.insert(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
//        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Tag::getId,id);
//        Tag tag = tagMapper.selectOne(queryWrapper);
//        TagPageVo tagPageVo = BeanCopyUtils.copyBean(tag, TagPageVo.class);
        tagMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateTag(Long id) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getId, id);
        Tag tag = tagMapper.selectOne(queryWrapper);

        tagMapper.updateById(tag);
        TagPageVo tagPageVo = BeanCopyUtils.copyBean(tag, TagPageVo.class);
        return ResponseResult.okResult(tagPageVo);
    }

    @Override
    public ResponseResult saveTag(Tag tag) {
        tagMapper.updateById(tag);
        return ResponseResult.okResult();
    }

    /**
     * 标签列表
     * @return
     */
    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getDelFlag,0);
        List<Tag> tags = tagMapper.selectList(queryWrapper);
        List<ListAllTagVo> listAllTagVos = BeanCopyUtils.copyBeanList(tags, ListAllTagVo.class);
        return ResponseResult.okResult(listAllTagVos);
    }
}


