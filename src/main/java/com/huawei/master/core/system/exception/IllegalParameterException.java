package com.huawei.master.core.system.exception;

import com.huawei.master.core.system.HttpCode;
import com.huawei.master.core.system.exception.BaseException;

public class IllegalParameterException extends BaseException {
    public IllegalParameterException() {
    }

    public IllegalParameterException(Throwable ex) {
        super(ex);
    }

    public IllegalParameterException(String message) {
        super(message);
    }

    public IllegalParameterException(String message, Throwable ex) {
        super(message, ex);
    }

    protected HttpCode getCode() {
        return HttpCode.BAD_REQUEST;
    }
}