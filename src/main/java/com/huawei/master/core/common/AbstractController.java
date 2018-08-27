package com.huawei.master.core.common;


import com.google.common.collect.Maps;
import com.huawei.master.core.system.exception.BaseException;
import com.huawei.master.core.system.HttpCode;
import com.huawei.master.core.system.exception.IllegalParameterException;
import com.huawei.master.core.utils.JacksonMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class AbstractController {

    /**
     * 设置成功响应代码
     */
    protected ResponseEntity<ModelMap> setSuccessModelMap() {
        return setSuccessModelMap(new ModelMap(), null);
    }

    /**
     * 设置成功响应代码
     */
    protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap) {
        return setSuccessModelMap(modelMap, null);
    }

    /**
     * 设置成功响应代码
     */
    protected ResponseEntity<ModelMap> setSuccessModelMap(Object data) {
        return setModelMap(new ModelMap(), HttpCode.OK, data);
    }

    /**
     * 设置成功响应代码
     */
    protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap, Object data) {
        return setModelMap(modelMap, HttpCode.OK, data);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(HttpCode code) {
        return setModelMap(new ModelMap(), code, null);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code) {
        return setModelMap(modelMap, code, null);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(HttpCode code, Object data) {
        return setModelMap(new ModelMap(), code, data);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code, Object data) {
        if (!modelMap.isEmpty()) {
            Map<String, Object> map = Maps.newLinkedHashMap();
            map.putAll(modelMap);
            modelMap.clear();
            for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
                String key = iterator.next();
                if (!key.startsWith("org.springframework.validation.BindingResult") && !key.equals("void")) {
                    modelMap.put(key, map.get(key));
                }
            }
        }
        if (data != null) {
           if (data instanceof List<?>) {
                modelMap.put("rows", data);
                modelMap.put("total", ((List<?>) data).size());
            } else {
                modelMap.put("data", data);
            }
        }
        modelMap.put("code", code.value().toString());
        modelMap.put("msg", code.msg());
        modelMap.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(modelMap);
    }

    /**
     * 异常处理
     */
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex)
            throws Exception {
        ModelMap modelMap = new ModelMap();
        if (ex instanceof BaseException) {
            ((BaseException) ex).handler(modelMap);
        } else if (ex instanceof IllegalArgumentException) {
            new IllegalParameterException(ex.getMessage()).handler(modelMap);
        } else if (ex instanceof UnauthorizedException) {
            modelMap.put("code", HttpCode.FORBIDDEN.value().toString());
            modelMap.put("msg", StringUtils.defaultIfBlank(ex.getMessage(), HttpCode.FORBIDDEN.msg()));
        } else {
            modelMap.put("code", HttpCode.INTERNAL_SERVER_ERROR.value().toString());
            String msg = StringUtils.defaultIfBlank(ex.getMessage(), HttpCode.INTERNAL_SERVER_ERROR.msg());
            modelMap.put("msg", msg);
        }
        response.setContentType("application/json;charset=UTF-8");
        modelMap.put("timestamp", System.currentTimeMillis());
        byte[] bytes = JacksonMapper.serialize(modelMap).getBytes();
        response.getOutputStream().write(bytes);
    }
}
