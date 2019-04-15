package com.huawei.master.plc.restful;

import com.huawei.master.core.bean.ObjectIdResp;
import com.huawei.master.core.utils.JacksonMapper;
import com.huawei.master.plc.dao.PlcRepository;
import com.huawei.master.plc.domain.Plc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "PLC管理", description = "PLC管理")
@RequestMapping(value = "/rest/plc")
public class PlcRestful {


    @Autowired
    private PlcRepository plcRepository;

    @PostMapping
    @ApiOperation(value = "创建PLC", notes = "根据plc对象创建设备")
    @ApiImplicitParam(name = "plc", value = "设备详细实体plc", required = true, dataType = "Plc")
    public String postPlc(@RequestBody Plc plc) {
        Plc store = plcRepository.save(plc);
        return JacksonMapper.serialize(new ObjectIdResp(store.getId()));
    }

    @GetMapping
    @ApiOperation(value = "获取PLC列表", notes = "获取所有PLC列表")
    public List<Plc> getPlcs() {
        return plcRepository.findAll();
    }

    @ApiOperation(value = "获取PLC详细信息", notes = "根据url的id来获取PLC详细信息")
    @ApiImplicitParam(name = "id", value = "PLC_ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Plc getPlc(@PathVariable String id) {
        Optional<Plc> op = plcRepository.findById(id);
        return op.isPresent() ? op.get() : null;
    }


    @ApiOperation(value = "更新PLC详细信息", notes = "根据id来指定更新对象，并根据传过来的信息来更新PLC详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "PLC_ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "plc", value = "设备详细实体plc", required = true, dataType = "Plc")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putPlc(@PathVariable String id, @RequestBody Plc plc) {
        plc.setId(id);
        Plc store = plcRepository.save(plc);
        return JacksonMapper.serialize(new ObjectIdResp(store.getId()));
    }

    @ApiOperation(value = "删除PLC", notes = "根据id来指定删除PLC")
    @ApiImplicitParam(name = "id", value = "PLC_ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deletePlc(@PathVariable String id) {
        plcRepository.deleteById(id);
        return "success";
    }
}
