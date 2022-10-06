package com.lsh.controller;

import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.LinkAddDto;
import com.lsh.domain.dto.LinkListDto;
import com.lsh.domain.dto.LinkUpdateDto;
import com.lsh.domain.entity.Link;
import com.lsh.mapper.LinkMapper;
import com.lsh.service.CommentService;
import com.lsh.service.LinkService;
import com.lsh.utils.BeanCopyUtils;
import com.lsh.utils.PathUtils;
import com.sun.org.apache.bcel.internal.generic.LMUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, LinkListDto linkListDto){
        return  linkService.listLinkComment(pageNum,pageSize,linkListDto);

    }

    /**
     * 友链分页查询
     * @param linkAddDto
     * @return
     */
    @PostMapping
    public ResponseResult addLink(@RequestBody LinkAddDto linkAddDto){
        Link link = BeanCopyUtils.copyBean(linkAddDto, Link.class);
        return linkService.addLink(link);

    }

    /**
     * 获取友链内容
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult selectLink(@PathVariable long id ){
        return linkService.selectLink(id);
    }

    /**
     * 修改友链
     * @param linkUpdateDto
     * @return
     */
    @PutMapping
    public ResponseResult updateLink(@RequestBody LinkUpdateDto linkUpdateDto){
        Link link = BeanCopyUtils.copyBean(linkUpdateDto, Link.class);
        return linkService.updateLink(link);

    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable Long id){
        return linkService.deleteLink(id);

    }


}
