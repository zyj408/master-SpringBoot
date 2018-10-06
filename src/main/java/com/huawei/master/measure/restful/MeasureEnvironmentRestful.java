package com.huawei.master.measure.restful;

import com.huawei.master.core.bean.ObjectIdResp;
import com.huawei.master.core.utils.JacksonMapper;
import com.huawei.master.measure.dao.MeasureEnvironmentRepository;
import com.huawei.master.measure.domain.MeasureEnvironment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@Api(value = "流量测量环境管理", description = "流量测量环境管理")
@RequestMapping(value = "/rest/measureEnvironment")
public class MeasureEnvironmentRestful {


    @Autowired
    private MeasureEnvironmentRepository measureEnvironmentRepository;

    @PostMapping
    @ApiOperation(value = "创建流量测量环境", notes = "根据MeasureEnvironment对象创建流量测量环境")
    @ApiImplicitParam(name = "measureEnvironment", value = "流量测量环境详细实体measureEnvironment", required = true, dataType = "MeasureEnvironment")
    public String postMeasureEnvironment(@RequestBody MeasureEnvironment measureEnvironment) {
        MeasureEnvironment store = measureEnvironmentRepository.save(measureEnvironment);
        return JacksonMapper.serialize(new ObjectIdResp(store.getId()));
    }

    @GetMapping
    @ApiOperation(value = "获取流量测量环境列表", notes = "获取所有流量测量环境列表")
    public List<MeasureEnvironment> getMeasureEnvironments() {
        return measureEnvironmentRepository.findAll();
    }

    @ApiOperation(value = "获取流量测量环境详细信息", notes = "根据url的id来获取流量测量环境详细信息")
    @ApiImplicitParam(name = "id", value = "流量测量环境ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MeasureEnvironment getMeasureEnvironment(@PathVariable String id) {
        Optional<MeasureEnvironment> op = measureEnvironmentRepository.findById(id);
        return op.isPresent() ? op.get() : null;
    }


    @ApiOperation(value = "更新流量测量环境详细信息", notes = "根据id来指定更新对象，并根据传过来的信息来更新流量测量环境详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "流量测量环境ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "measureEnvironment", value = "流量测量环境详细实体measureEnvironment", required = true, dataType = "MeasureEnvironment")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putMeasureEnvironment(@PathVariable String id, @RequestBody MeasureEnvironment measureEnvironment) {
        measureEnvironment.setId(id);
        MeasureEnvironment store = measureEnvironmentRepository.save(measureEnvironment);
        return JacksonMapper.serialize(new ObjectIdResp(store.getId()));
    }

    @ApiOperation(value = "删除流量测量环境", notes = "根据id来指定删除流量测量环境")
    @ApiImplicitParam(name = "id", value = "流量测量环境ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteMeasureEnvironment(@PathVariable String id) {
        measureEnvironmentRepository.deleteById(id);
        return "success";
    }
}
