package com.huawei.master.app.restful;

import com.huawei.master.app.dao.CommentRepository;
import com.huawei.master.app.domain.Comment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "评论管理", description = "评论管理")
@RequestMapping(value = "/rest/comment")
public class CommentRestful {

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping
    @ApiOperation(value = "创建评论", notes = "根据comment对象创建评论")
    @ApiImplicitParam(name = "comment", value = "评论详细实体comment", required = true, dataType = "Comment")
    public String postComment(@RequestBody Comment comment) {
        commentRepository.save(comment);
        return "success";
    }

    @GetMapping
    @ApiOperation(value = "获取评论列表", notes = "获取所有评论列表")
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    @ApiOperation(value = "获取评论详细信息", notes = "根据url的id来获取评论详细信息")
    @ApiImplicitParam(name = "id", value = "评论ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Comment getComment(@PathVariable String id) {
        Optional<Comment> op = commentRepository.findById(id);
        return op.isPresent() ? op.get() : null;
    }


    @ApiOperation(value = "更新评论详细信息", notes = "根据id来指定更新对象，并根据传过来的信息来更新评论详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "评论ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "comment", value = "评论详细实体comment", required = true, dataType = "Comment")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putComment(@PathVariable String id, @RequestBody Comment comment) {
        comment.setId(id);
        commentRepository.save(comment);
        return "success";
    }

    @ApiOperation(value = "删除评论", notes = "根据id来指定删除评论")
    @ApiImplicitParam(name = "id", value = "评论ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteComment(@PathVariable String id) {
        commentRepository.deleteById(id);
        return "success";
    }
}
