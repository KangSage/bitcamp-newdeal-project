package xyz.breadcrumb.web;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.breadcrumb.domain.Amount;
import xyz.breadcrumb.service.AmountService;

@RestController
@RequestMapping("/amount")
public class AmountController {
    
    @Autowired AmountService amountService;
    
    @PostMapping("add")
    public Object add(Amount amount) throws Exception {
        System.out.println(amount);
        HashMap<String,Object> result = new HashMap<>();
//        amountService.add(amount);
        result.put("status", "success");
        return result;
    }
}
