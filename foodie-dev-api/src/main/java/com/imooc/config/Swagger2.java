package com.imooc.config;

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

/**
 * @description:
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-06-29
 */
@Configuration
@EnableSwagger2
public class Swagger2 {


    //    http://localhost:8081/swagger-ui.html     原路径
    //    http://localhost:8081/doc.html     原路径

    /*
     * @Description  配置Swagger2核心配置Docket
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/6/29 22:21
     * @Param []
     * @return springfox.documentation.spring.web.plugins.Docket
     **/
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2) //指定Api类型为Swagger2
                .apiInfo(createApiInfo())   //用于定义API文档汇总信息
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.imooc.controller")) //指定Controller包
                .paths(PathSelectors.any()) //所有Controller
                .build();

    }

    private ApiInfo createApiInfo(){
        return  new ApiInfoBuilder()
                .title("天天吃货 电商平台 接口API") //文档标题页面
                .contact(new Contact("Charles","http://www.sweetriver.com","cgde163@163.com")) //联系人信息
                .description("专为天天吃货提供的API文档") //描述
                .version("1.0.1")//文档版本号
                .termsOfServiceUrl("http://www.sweetriver.com") //网站地址
                .build();
    }
}