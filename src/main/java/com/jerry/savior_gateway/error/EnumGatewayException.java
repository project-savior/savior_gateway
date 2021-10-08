package com.jerry.savior_gateway.error;

import com.jerry.savior_common.asserts.BusinessExceptionAssert;

/**
 * @author 22454
 */
public enum EnumGatewayException implements BusinessExceptionAssert {
    UNAUTHORIZED(200001, "未登录，请登录后重试！");
    private final Integer code;
    private final String message;

    EnumGatewayException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
