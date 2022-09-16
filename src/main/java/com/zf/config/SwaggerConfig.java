package com.zf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**swagger帮助生成接口文档
     * 1：配置生成的文档信息
     * 2：配置生成规则*/

    /**Docket封装接口文档信息*/
    @Bean
    public Docket getDocket(){
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();//多态new实现类
        apiInfoBuilder.title("智菲名片夹");//文档标题    支持链式调用
        apiInfoBuilder.description("《智菲名片夹》后端接口说明描述");//说明
        apiInfoBuilder.version(" v 2.0.1");
        apiInfoBuilder.contact(new Contact("智菲-10小组","暂无","2293667568@qq.com"));
        ApiInfo apiInfo =apiInfoBuilder.build();
        /**如果获取一个接口对象
         * new接口,需要在构造器后的花括号中实现所有的抽象方法
         * new 子类/实现类
         * 工厂模式*/
        Docket docket =new Docket(DocumentationType.SWAGGER_2)//指定文档风格  //指定生成的文档中的封面信息 : 文档标题，版本，作者
                .apiInfo(apiInfo)
                .select()//生成策略
                .apis(RequestHandlerSelectors.basePackage("com.zf.controller"))
                .paths(PathSelectors.any())//为所有的controller的生成接口
                .build();
        return docket;
    }

}
