package com.huawei.master.measure.restful;

import com.huawei.master.core.bean.ObjectIdResp;
import com.huawei.master.core.utils.JacksonMapper;
import com.huawei.master.measure.dao.FlowMeasureRepository;
import com.huawei.master.measure.domain.FlowMeasure;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@Api(value = "流量测量结果管理", description = "流量测量结果管理")
@RequestMapping(value = "/rest/flowMeasure")
public class FlowMeasureRestful {


    @Autowired
    private FlowMeasureRepository flowMeasureRepository;

    @PostMapping
    @ApiOperation(value = "创建流量测量结果", notes = "根据FlowMeasure对象创建流量测量结果")
    @ApiImplicitParam(name = "flowMeasure", value = "流量测量结果详细实体flowMeasure", required = true, dataType = "FlowMeasure")
    public String postFlowMeasure(@RequestBody FlowMeasure flowMeasure) {
        FlowMeasure store = flowMeasureRepository.save(flowMeasure);
        return JacksonMapper.serialize(new ObjectIdResp(store.getId()));
    }

    @GetMapping
    @ApiOperation(value = "获取流量测量结果列表", notes = "获取所有流量测量结果列表")
    public List<FlowMeasure> getFlowMeasures() {
        return flowMeasureRepository.findAll();
    }

    @ApiOperation(value = "获取流量测量结果详细信息", notes = "根据url的id来获取流量测量结果详细信息")
    @ApiImplicitParam(name = "id", value = "流量测量结果ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public FlowMeasure getFlowMeasure(@PathVariable String id) {
        Optional<FlowMeasure> op = flowMeasureRepository.findById(id);
        return op.isPresent() ? op.get() : null;
    }


    @ApiOperation(value = "更新流量测量结果详细信息", notes = "根据id来指定更新对象，并根据传过来的信息来更新流量测量结果详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "流量测量结果ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "flowMeasure", value = "流量测量结果详细实体flowMeasure", required = true, dataType = "FlowMeasure")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putFlowMeasure(@PathVariable String id, @RequestBody FlowMeasure flowMeasure) {
        flowMeasure.setId(id);
        FlowMeasure store = flowMeasureRepository.save(flowMeasure);
        return JacksonMapper.serialize(new ObjectIdResp(store.getId()));
    }

    @ApiOperation(value = "删除流量测量结果", notes = "根据id来指定删除流量测量结果")
    @ApiImplicitParam(name = "id", value = "流量测量结果ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteFlowMeasure(@PathVariable String id) {
        flowMeasureRepository.deleteById(id);
        return "success";
    }
}
