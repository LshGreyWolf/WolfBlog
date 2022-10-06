package com.lsh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.LinkListDto;
import com.lsh.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-09-30 19:35:49
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult listLinkComment(Integer pageNum, Integer pageSize, LinkListDto linkListDto);

    ResponseResult addLink(Link link);

    ResponseResult selectLink(long id);

    ResponseResult updateLink(Link link);

    ResponseResult deleteLink(Long id);
}

