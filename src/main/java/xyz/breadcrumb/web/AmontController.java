package xyz.breadcrumb.web;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/amont")
public class AmontController {
    @RequestMapping("/amont")
    public Object aomnt() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("memo", "홍길동");
        return result;
    }
}
