package xyz.breadcrumb.web;

import java.util.HashMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/amount")
public class AmountController {
    @PostMapping("/amount")
    public Object amount() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("memo", "홍길동");
        return result;
    }
}
