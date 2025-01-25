package com.fastcampus.boardserver.config;

import com.fastcampus.boardserver.dto.request.PostSearchRequest;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("customKeyGenerator")
public class CustomKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        if (params.length == 0 || params[0] == null) {
            return "getPosts:null:null";
        }
        PostSearchRequest request = (PostSearchRequest) params[0];
        return "getPosts:" + request.getName() + ":" + request.getCategoryId();
    }
}
