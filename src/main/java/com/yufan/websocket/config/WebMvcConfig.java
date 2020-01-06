package com.yufan.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lirf
 * @version 1.0 (下面所有处理方法待验证是否有效)
 * @date 2019/12/9 14:11
 *  问题：
 *  Access to XMLHttpRequest at 'http://localhost:8080/hello' from origin 'http://localhost:8080'
 *  has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
 * @describe  通过CORS解决跨域问题(方式1) 如下
 * 解决方法2： 在 请求方法类的对应方法上中加上 @CrossOrigin(value = "http://localhost:8080")
 */
/*@Configuration*/
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:8080")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

}
