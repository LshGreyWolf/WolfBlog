package com.lsh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.Constants.SystemConstants;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.LinkListDto;
import com.lsh.domain.entity.Link;
import com.lsh.domain.vo.LinkListVo;
import com.lsh.domain.vo.LinkVo;
import com.lsh.domain.vo.PageVo;
import com.lsh.mapper.LinkMapper;
import com.lsh.service.LinkService;
import com.lsh.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-09-30 19:35:49
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Autowired
    private LinkMapper linkMapper;

    /**
     * 友链
     *
     * @return
     */
    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的   select * from sg_link where status = 0;
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = linkMapper.selectList(queryWrapper);
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult listLinkComment(Integer pageNum, Integer pageSize, LinkListDto linkListDto) {

        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(linkListDto.getName()), Link::getName, linkListDto.getName());
        queryWrapper.eq(StringUtils.hasText(linkListDto.getStatus()), Link::getName, linkListDto.getStatus());
        Page<Link> linkPage = new Page<>(pageNum, pageSize);

        Page<Link> page = linkMapper.selectPage(linkPage, queryWrapper);
        List<Link> records = page.getRecords();
        List<LinkListVo> linkListVos = BeanCopyUtils.copyBeanList(records, LinkListVo.class);
        long total = page.getTotal();
        PageVo pageVo = new PageVo(linkListVos, total);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addLink(Link link) {
        linkMapper.insert(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectLink(long id) {
        Link link = linkMapper.selectOne(new LambdaQueryWrapper<Link>().eq(Link::getId, id));
        LinkListVo linkListVo = BeanCopyUtils.copyBean(link, LinkListVo.class);
        return ResponseResult.okResult(linkListVo);
    }

    @Override
    public ResponseResult updateLink(Link link) {
        linkMapper.updateById(link);


        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(Long id) {
        linkMapper.deleteById(id);

        return ResponseResult.okResult();
    }
}

