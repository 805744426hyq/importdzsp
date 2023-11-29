package com.swsk.frame.springboot.business.rgyx.ynaqsj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration(value = "rgyx_swagger")
public class SwaggerConfig {

    @Bean
    public Docket SystemApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(ApiInfo())
                .groupName("西北人影导入地面历史作业到电子沙盘")
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.swsk.frame.springboot.business.rgyx.ynaqsj"))
                .build();
    }


    //配置swagger信息的ApiInfo
    private ApiInfo ApiInfo() {
        return new ApiInfoBuilder()
                .title("西北人影导入地面历史作业到电子沙盘服务")
                .version("1.0")
                .build();
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                // 隐藏UI上的Models模块
                .defaultModelsExpandDepth(-1)
                .defaultModelExpandDepth(0)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .validatorUrl(null)
                .build();
    }
}
