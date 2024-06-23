package kr.co.study.restfulservice.controller;

import kr.co.study.restfulservice.bean.HelloBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello-world-bean")
    public HelloBean helloBean() {
        return new HelloBean("Hello world");
    }

    @GetMapping("/hello-world-bean/path-variable/{name}")
    public HelloBean helloBeanPathVariable(@PathVariable String name) {
        return new HelloBean(String.format("Hello world, %s", name));
    }
}
