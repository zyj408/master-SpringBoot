package com.huawei.master.iot.restful;

import com.huawei.master.core.bean.ObjectIdResp;
import com.huawei.master.core.utils.JacksonMapper;
import com.huawei.master.iot.dao.SensorRepository;
import com.huawei.master.iot.domain.Sensor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "传感器管理", description = "传感器管理")
@RequestMapping(value = "/rest/sensor")
public class SensorRestful {

    @Autowired
    private SensorRepository sensorRepository;

    @PostMapping
    @ApiOperation(value = "创建传感器", notes = "根据sensor对象创建传感器")
    @ApiImplicitParam(name = "sensor", value = "传感器详细实体sensor", required = true, dataType = "Sensor")
    public String postSensor(@RequestBody Sensor sensor) {
        Sensor store = sensorRepository.save(sensor);
        return JacksonMapper.serialize(new ObjectIdResp(store.getId()));
    }

    @GetMapping
    @ApiOperation(value = "获取传感器列表", notes = "获取所有传感器列表")
    public List<Sensor> getSensors() {
        return sensorRepository.findAll();
    }

    @ApiOperation(value = "获取传感器详细信息", notes = "根据url的id来获取传感器详细信息")
    @ApiImplicitParam(name = "id", value = "传感器ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Sensor getSensor(@PathVariable String id) {
        Optional<Sensor> op = sensorRepository.findById(id);
        return op.isPresent() ? op.get() : null;
    }


    @ApiOperation(value = "更新传感器详细信息", notes = "根据id来指定更新对象，并根据传过来的信息来更新传感器详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "传感器ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "sensor", value = "传感器详细实体sensor", required = true, dataType = "Sensor")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putSensor(@PathVariable String id, @RequestBody Sensor sensor) {
        sensor.setId(id);
        sensorRepository.save(sensor);
        return "success";
    }

    @ApiOperation(value = "删除传感器", notes = "根据id来指定删除传感器")
    @ApiImplicitParam(name = "id", value = "传感器ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteSensor(@PathVariable String id) {
        sensorRepository.deleteById(id);
        return "success";
    }
}
