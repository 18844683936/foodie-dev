package com.imooc.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @description:
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-06-24
 */
@RestController
@ApiIgnore //SWAGGER 忽略该Controller
public class HelloController {

    @GetMapping("/hello")
    public Object hello(){
        return "Hello World";
    }
}