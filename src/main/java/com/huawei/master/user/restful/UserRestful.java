package com.huawei.master.user.restful;

import com.huawei.master.core.bean.ObjectIdResp;
import com.huawei.master.core.utils.JacksonMapper;
import com.huawei.master.user.dao.UserRepository;
import com.huawei.master.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Api(value = "用户管理", description = "用户管理")
@RequestMapping(value = "/rest/user")
public class UserRestful {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    public String postUser(@RequestBody User user) {
        User store = userRepository.save(user);
        return JacksonMapper.serialize(new ObjectIdResp(store.getId()));
    }

    @GetMapping
    @ApiOperation(value = "获取用户列表", notes = "获取所有用户列表")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable String id) {
        Optional<User> op = userRepository.findById(id);
        return op.isPresent() ? op.get() : null;
    }

    @ApiOperation(value = "更新用户详细信息", notes = "根据id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable String id, @RequestBody User user) {
        user.setId(id);
        User store = userRepository.save(user);
        return JacksonMapper.serialize(new ObjectIdResp(store.getId()));
    }

    @ApiOperation(value = "删除用户", notes = "根据id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
        return "success";
    }

}