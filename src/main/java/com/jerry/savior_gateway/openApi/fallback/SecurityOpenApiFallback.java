package com.jerry.savior_gateway.openApi.fallback;

import com.jerry.savior_common.constants.StandardResponse;
import com.jerry.savior_common.error.BusinessException;
import com.jerry.savior_common.response.CommonResponse;
import com.jerry.savior_gateway.openApi.SecurityOpenApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 22454
 */
@Slf4j
@Component
public class SecurityOpenApiFallback implements SecurityOpenApi {
    @Override
    public CommonResponse<Void> authentication(String auth, String token) {
        log.error("调用Oauth服务进行鉴权失败，找不到服务Provider");
        throw new BusinessException(StandardResponse.ERROR);
    }
}
