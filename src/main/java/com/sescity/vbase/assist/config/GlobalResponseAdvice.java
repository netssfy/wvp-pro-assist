package com.sescity.vbase.assist.config;

import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import com.sescity.vbase.assist.controller.bean.ErrorCode;
import com.sescity.vbase.assist.controller.bean.WVPResult;

import javax.validation.constraints.NotNull;

/**
 * 全局统一返回结果
 * @author lin
 */
@RestControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(@NotNull MethodParameter returnType, @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, @NotNull MethodParameter returnType, @NotNull MediaType selectedContentType, @NotNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response) {
        // 排除api文档的接口，这个接口不需要统一
        String[] excludePath = {"/v3/api-docs","/api/v1","/index/hook"};
        for (String path : excludePath) {
            if (request.getURI().getPath().startsWith(path)) {
                return body;
            }
        }

        if (body instanceof WVPResult) {
            return body;
        }

        if (body instanceof ErrorCode) {
            ErrorCode errorCode = (ErrorCode) body;
            return new WVPResult<>(errorCode.getCode(), errorCode.getMsg(), null);
        }

        if (body instanceof String) {
            return JSON.toJSONString(WVPResult.success(body));
        }

        return WVPResult.success(body);
    }
}
