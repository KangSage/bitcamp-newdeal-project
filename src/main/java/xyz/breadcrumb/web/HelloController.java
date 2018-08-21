package xyz.breadcrumb.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public Object hello() {
        System.out.println("안녕!");
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", "홍길동");
        return result;
    }
}
