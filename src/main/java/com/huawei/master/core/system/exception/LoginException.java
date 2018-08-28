package com.huawei.master.core.system.exception;

import com.huawei.master.core.system.HttpCode;

public class LoginException extends BaseException {
    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Exception e) {
        super(message, e);
    }

    protected HttpCode getCode() {
        return HttpCode.LOGIN_FAIL;
    }
}
