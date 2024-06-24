package kr.co.study.restfulservice.controller;

import kr.co.study.restfulservice.bean.HelloBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloController {

    @Autowired
    private MessageSource messageSource;

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

    @GetMapping("/hello-world-internationalized")
    public String helloInternationalized(@RequestHeader(name = "Accept-Language", required = false)Locale locale)
    {
        return messageSource.getMessage("greeting.message", null, locale);
    }
}
