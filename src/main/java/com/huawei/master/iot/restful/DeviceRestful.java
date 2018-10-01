package com.huawei.master.iot.restful;

import com.huawei.master.core.bean.ObjectIdResp;
import com.huawei.master.core.utils.JacksonMapper;
import com.huawei.master.iot.dao.DeviceRepository;
import com.huawei.master.iot.domain.Device;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "设备管理", description = "设备管理")
@RequestMapping(value = "/rest/device")
public class DeviceRestful {

    @Autowired
    private DeviceRepository deviceRepository;

    @PostMapping
    @ApiOperation(value = "创建设备", notes = "根据device对象创建设备")
    @ApiImplicitParam(name = "device", value = "设备详细实体device", required = true, dataType = "Device")
    public String postDevice(@RequestBody Device device) {
        Device store = deviceRepository.save(device);
        return JacksonMapper.serialize(new ObjectIdResp(store.getId()));
    }

    @GetMapping
    @ApiOperation(value = "获取设备列表", notes = "获取所有设备列表")
    public List<Device> getDevices() {
        return deviceRepository.findAll();
    }

    @ApiOperation(value = "获取设备详细信息", notes = "根据url的id来获取设备详细信息")
    @ApiImplicitParam(name = "id", value = "设备ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Device getDevice(@PathVariable String id) {
        Optional<Device> op = deviceRepository.findById(id);
        return op.isPresent() ? op.get() : null;
    }


    @ApiOperation(value = "更新设备详细信息", notes = "根据id来指定更新对象，并根据传过来的信息来更新设备详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "设备ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "device", value = "设备详细实体device", required = true, dataType = "Device")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putDevice(@PathVariable String id, @RequestBody Device device) {
        device.setId(id);
        deviceRepository.save(device);
        return "success";
    }

    @ApiOperation(value = "删除设备", notes = "根据id来指定删除设备")
    @ApiImplicitParam(name = "id", value = "设备ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteDevice(@PathVariable String id) {
        deviceRepository.deleteById(id);
        return "success";
    }
}
