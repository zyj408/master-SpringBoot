package com.huawei.master.measure.restful;

import com.huawei.master.core.bean.ObjectIdResp;
import com.huawei.master.core.utils.JacksonMapper;
import com.huawei.master.measure.dao.ProcedureRepository;
import com.huawei.master.measure.domain.Procedure;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "测量过程管理", description = "测量过程管理")
@RequestMapping(value = "/rest/procedure")
public class ProcedureRestful {

    @Autowired
    private ProcedureRepository procedureRepository;

    @PostMapping
    @ApiOperation(value = "创建测量过程", notes = "根据FlowMeasure对象创建测量过程")
    @ApiImplicitParam(name = "procedure", value = "测量过程详细实体procedure", required = true, dataType = "Procedure")
    public String postFlowMeasure(@RequestBody Procedure procedure) {
        Procedure store = procedureRepository.save(procedure);
        return JacksonMapper.serialize(new ObjectIdResp(store.getId()));
    }

    @GetMapping
    @ApiOperation(value = "获取测量过程列表", notes = "获取所有测量过程列表")
    public List<Procedure> getFlowMeasures() {
        return procedureRepository.findAll();
    }

    @ApiOperation(value = "获取测量过程详细信息", notes = "根据url的id来获取测量过程详细信息")
    @ApiImplicitParam(name = "id", value = "测量过程ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Procedure getFlowMeasure(@PathVariable String id) {
        Optional<Procedure> op = procedureRepository.findById(id);
        return op.isPresent() ? op.get() : null;
    }


    @ApiOperation(value = "更新测量过程详细信息", notes = "根据id来指定更新对象，并根据传过来的信息来更新测量过程详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "测量过程ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "procedure", value = "测量过程详细实体procedure", required = true, dataType = "Procedure")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putFlowMeasure(@PathVariable String id, @RequestBody Procedure procedure) {
        procedure.setId(id);
        Procedure store = procedureRepository.save(procedure);
        return JacksonMapper.serialize(new ObjectIdResp(store.getId()));
    }

    @ApiOperation(value = "删除测量过程", notes = "根据id来指定删除测量过程")
    @ApiImplicitParam(name = "id", value = "测量过程ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteFlowMeasure(@PathVariable String id) {
        procedureRepository.deleteById(id);
        return "success";
    }
}
