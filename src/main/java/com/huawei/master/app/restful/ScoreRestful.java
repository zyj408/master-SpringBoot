package com.huawei.master.app.restful;

import com.huawei.master.app.dao.ScoreRepository;
import com.huawei.master.app.domain.Score;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "评分管理", description = "评分管理")
@RequestMapping(value = "/rest/score")
public class ScoreRestful {


    @Autowired
    private ScoreRepository scoreRepository;

    @PostMapping
    @ApiOperation(value = "创建评分", notes = "根据Score对象创建评分")
    @ApiImplicitParam(name = "score", value = "评分详细实体Score", required = true, dataType = "Score")
    public String postScore(@RequestBody Score score) {
        scoreRepository.save(score);
        return "success";
    }

    @GetMapping
    @ApiOperation(value = "获取评分列表", notes = "获取所有评分列表")
    public List<Score> getScores() {
        return scoreRepository.findAll();
    }

    @ApiOperation(value = "获取评分详细信息", notes = "根据url的id来获取评分详细信息")
    @ApiImplicitParam(name = "id", value = "评分ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Score getScore(@PathVariable String id) {
        Optional<Score> op = scoreRepository.findById(id);
        return op.isPresent() ? op.get() : null;
    }


    @ApiOperation(value = "更新评分详细信息", notes = "根据id来指定更新对象，并根据传过来的信息来更新评分详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "评分ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "score", value = "评分详细实体Score", required = true, dataType = "Score")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putScore(@PathVariable String id, @RequestBody Score score) {
        score.setId(id);
        scoreRepository.save(score);
        return "success";
    }

    @ApiOperation(value = "删除评分", notes = "根据id来指定删除评分")
    @ApiImplicitParam(name = "id", value = "评分ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteScore(@PathVariable String id) {
        scoreRepository.deleteById(id);
        return "success";
    }
}
