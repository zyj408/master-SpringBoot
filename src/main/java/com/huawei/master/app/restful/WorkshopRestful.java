package com.huawei.master.app.restful;

import com.huawei.master.app.dao.WorkshopRepository;
import com.huawei.master.app.domain.Workshop;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "讨论会管理", description = "讨论会管理")
@RequestMapping(value = "/rest/workshop")
public class WorkshopRestful {

    @Autowired
    private WorkshopRepository workshopRepository;

    @PostMapping
    @ApiOperation(value = "创建讨论会", notes = "根据Workshop对象创建讨论会")
    @ApiImplicitParam(name = "Workshop", value = "讨论会详细实体Workshop", required = true, dataType = "Workshop")
    public String postWorkshop(@RequestBody Workshop workshop) {
        workshopRepository.save(workshop);
        return "success";
    }

    @GetMapping
    @ApiOperation(value = "获取讨论会列表", notes = "获取所有讨论会列表")
    public List<Workshop> getWorkshops() {
        return workshopRepository.findAll();
    }

    @ApiOperation(value = "获取讨论会详细信息", notes = "根据url的id来获取讨论会详细信息")
    @ApiImplicitParam(name = "id", value = "讨论会ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Workshop getWorkshop(@PathVariable String id) {
        Optional<Workshop> op = workshopRepository.findById(id);
        return op.isPresent() ? op.get() : null;
    }


    @ApiOperation(value = "更新讨论会详细信息", notes = "根据id来指定更新对象，并根据传过来的信息来更新讨论会详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "讨论会ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "Workshop", value = "讨论会详细实体Workshop", required = true, dataType = "Workshop")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putWorkshop(@PathVariable String id, @RequestBody Workshop workshop) {
        workshop.setId(id);
        workshopRepository.save(workshop);
        return "success";
    }

    @ApiOperation(value = "删除讨论会", notes = "根据id来指定删除讨论会")
    @ApiImplicitParam(name = "id", value = "讨论会ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteWorkshop(@PathVariable String id) {
        workshopRepository.deleteById(id);
        return "success";
    }
}
