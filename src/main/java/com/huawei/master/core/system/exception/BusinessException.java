package com.huawei.master.core.system.exception;

import com.huawei.master.core.system.HttpCode;

public class BusinessException extends BaseException {
    public BusinessException() {
    }

    public BusinessException(Throwable ex) {
        super(ex);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable ex) {
        super(message, ex);
    }

    protected HttpCode getCode() {
        return HttpCode.CONFLICT;
    }
}