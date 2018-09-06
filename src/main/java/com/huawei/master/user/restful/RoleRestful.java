package com.huawei.master.user.restful;

import com.huawei.master.user.dao.RoleRepository;
import com.huawei.master.user.domain.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "角色管理", description = "角色管理")
@RequestMapping(value = "/rest/role")
public class RoleRestful {

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping
    @ApiOperation(value = "创建角色", notes = "根据Role对象创建角色")
    @ApiImplicitParam(name = "role", value = "角色详细实体role", required = true, dataType = "Role")
    public String postRole(@RequestBody Role role) {
        roleRepository.save(role);
        return "success";
    }

    @GetMapping
    @ApiOperation(value = "获取角色列表", notes = "获取所有角色列表")
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @ApiOperation(value = "获取角色详细信息", notes = "根据url的id来获取角色详细信息")
    @ApiImplicitParam(name = "id", value = "角色ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Role getRole(@PathVariable String id) {
        Optional<Role> op = roleRepository.findById(id);
        return op.isPresent() ? op.get() : null;
    }


    @ApiOperation(value = "更新角色详细信息", notes = "根据id来指定更新对象，并根据传过来的信息来更新角色详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "role", value = "角色详细实体role", required = true, dataType = "Role")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putRole(@PathVariable String id, @RequestBody Role role) {
        role.setId(id);
        roleRepository.save(role);
        return "success";
    }

    @ApiOperation(value = "删除角色", notes = "根据id来指定删除角色")
    @ApiImplicitParam(name = "id", value = "角色ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteRole(@PathVariable String id) {
        roleRepository.deleteById(id);
        return "success";
    }
}
