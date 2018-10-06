package com.huawei.master.app.restful;


import com.huawei.master.app.dao.ChapterRepository;
import com.huawei.master.app.domain.Chapter;
import com.huawei.master.core.bean.ObjectIdResp;
import com.huawei.master.core.utils.JacksonMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "章节管理", description = "章节管理")
@RequestMapping(value = "/rest/chapter")
public class ChapterRestful {

    @Autowired
    private ChapterRepository chapterRepository;

    @PostMapping
    @ApiOperation(value = "创建章节", notes = "根据chapter对象创建章节")
    @ApiImplicitParam(name = "chapter", value = "章节详细实体chapter", required = true, dataType = "Chapter")
    public String postChapter(@RequestBody Chapter chapter) {
        Chapter store = chapterRepository.save(chapter);
        return JacksonMapper.serialize(new ObjectIdResp(store.getId()));
    }

    @GetMapping
    @ApiOperation(value = "获取章节列表", notes = "获取所有章节列表")
    public List<Chapter> getChapters() {
        return chapterRepository.findAll();
    }

    @ApiOperation(value = "获取章节详细信息", notes = "根据url的id来获取章节详细信息")
    @ApiImplicitParam(name = "id", value = "章节ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Chapter getChapter(@PathVariable String id) {
        Optional<Chapter> op = chapterRepository.findById(id);
        return op.isPresent() ? op.get() : null;
    }


    @ApiOperation(value = "更新章节详细信息", notes = "根据id来指定更新对象，并根据传过来的信息来更新章节详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "章节ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "chapter", value = "章节详细实体chapter", required = true, dataType = "Chapter")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putChapter(@PathVariable String id, @RequestBody Chapter chapter) {
        chapter.setId(id);
        Chapter store = chapterRepository.save(chapter);
        return JacksonMapper.serialize(new ObjectIdResp(store.getId()));
    }

    @ApiOperation(value = "删除章节", notes = "根据id来指定删除章节")
    @ApiImplicitParam(name = "id", value = "章节ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteChapter(@PathVariable String id) {
        chapterRepository.deleteById(id);
        return "success";
    }
}
