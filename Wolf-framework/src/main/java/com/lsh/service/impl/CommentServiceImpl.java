package com.lsh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.Constants.SystemConstants;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.LinkListDto;
import com.lsh.domain.entity.Comment;
import com.lsh.domain.entity.Link;
import com.lsh.domain.vo.CommentVo;
import com.lsh.domain.vo.PageVo;
import com.lsh.enums.AppHttpCodeEnum;
import com.lsh.exception.SystemException;
import com.lsh.mapper.CommentMapper;
import com.lsh.service.UserService;
import com.lsh.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lsh.service.CommentService;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-10-02 17:39:25
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论  root_id = -1

        //对文章id进行判断
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId, articleId);
        //根评论  root_id = -1
        queryWrapper.eq(Comment::getRootId, -1);
        //评论类型
        queryWrapper.eq(Comment::getType,commentType);
        //分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        Page<Comment> commentPage = commentMapper.selectPage(page, queryWrapper);
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }


    /**
     * 对Username，，，ToCommentUserName  进行赋值
     *
     * @param list
     * @return
     */
    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历vo集合
        for (CommentVo commentVo : commentVos) {
            //通过creatyBy查询用户的昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if (commentVo.getToCommentUserId() != -1) {
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }

    /**
     * 根据根评论的id查询所对应的子评论的集合
     *
     * @param id 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }

    /**
     * 发表评论和回复评论
     *
     * @param comment
     * @return
     */
    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不为空
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENTNOTNULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }



}

