package com.lsh.controller;

import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.TagListDto;
import com.lsh.domain.entity.Tag;
import com.lsh.domain.vo.TagPageVo;
import com.lsh.service.TagService;
import com.lsh.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 标签分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param tagListDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {

        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    /**
     * 新增标签
     *
     * @param tagListDto
     * @return
     */
    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        return tagService.addTag(tag);
    }

    /**
     * 删除标签
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id) {
        return tagService.deleteTag(id);
    }

    /**
     * 获取标签内容
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult updateTag(@PathVariable Long id) {
        return tagService.updateTag(id);
    }

    @PutMapping
    public ResponseResult saveTag(@RequestBody TagPageVo tagPageVo) {
        Tag tag = BeanCopyUtils.copyBean(tagPageVo, Tag.class);
        return tagService.saveTag(tag);

    }

    /**
     * 标签列表
     * @return
     */
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){

        return tagService.listAllTag();
    }

}
