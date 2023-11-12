package com.blog.personalblog.interceptor;

import com.blog.personalblog.utils.JwtUtil;
import com.blog.personalblog.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//       验证令牌
        String header = request.getHeader("Authorization");
        try {
            //重redis中获取token
            ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
            String redisToken = stringStringValueOperations.get(header);
            if(redisToken==null){
                throw new RuntimeException();
            }
            Map<String, Object> parseToken = JwtUtil.parseToken(header);
            //把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(parseToken);
            //放行
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            //不放行
            return false;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
