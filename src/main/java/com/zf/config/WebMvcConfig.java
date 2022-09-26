package com.zf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
//          .addResourceLocations("file:" + System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\")
                .addResourceLocations("file:" +"/www/myproject/image/");
    }
}



