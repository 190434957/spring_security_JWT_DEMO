package indi.a9043.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /**
     * 不受保护资源
     *
     * @return
     */
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    /**
     * 受保护资源
     *
     * @return
     */
    @GetMapping("/resource")
    public String resource() {
        return "resource";
    }
}
