package com.lsh.controller;

import com.lsh.Constants.SystemConstants;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.AddCommentDto;
import com.lsh.domain.entity.Comment;
import com.lsh.service.CommentService;
import com.lsh.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论",description = "评论相关接口")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 评论列表展示
     *
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId, pageNum, pageSize);
    }

    /**
     * 发表评论和回复评论
     *
     *
     * @return
     */
    @PostMapping
    public ResponseResult addComment(@RequestBody AddCommentDto commentDto) {
        Comment comment = BeanCopyUtils.copyBean(commentDto, Comment.class);
        return commentService.addComment(comment);

    }

    /**
     * 友链评论展示
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/linkCommentList")
    @ApiOperation(value = "友链评论列表",notes = "获取一页友链评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "页码")
    })
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        //直接调用之前的评论列表方法
        return commentService.commentList(SystemConstants.LINK_COMMENT, null,pageNum,pageSize);
    }
}
