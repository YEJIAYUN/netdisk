package com.ustc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 叶嘉耘
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket createRestApi() {
        //文档类型,Swagger
        return new Docket(DocumentationType.SWAGGER_2)
                // 设置API信息
                .apiInfo(this.apiInfo())
                // 扫描controller, 获取API接口
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ustc.upload.controller"))
                .paths(PathSelectors.any())
                // 构建出Docket对象
                .build();
    }

    /**
     * 创建API基本信息
     * @return API信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("上传、下载接口文档")
                .description("上传、下载功能接口文档")
                .version("1.0")
                .build();
    }
}
