package com.jerry.savior_gateway.openApi;


import com.jerry.savior_common.response.CommonResponse;
import com.jerry.savior_gateway.openApi.fallback.SecurityOpenApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 22454
 */
@FeignClient(name = "savior-oauth",
        path = "/oauth",
        fallback = SecurityOpenApiFallback.class)
public interface SecurityOpenApi {
    /**
     * 鉴定token是否有效
     *
     * @param token token
     * @return 是否有效
     */
    @GetMapping("/authentication")
    CommonResponse<Void> authentication(@RequestParam String token);

}
