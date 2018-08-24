package xyz.breadcrumb.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.breadcrumb.domain.Amount;
import xyz.breadcrumb.domain.Member;
import xyz.breadcrumb.service.AmountService;

@RestController
@RequestMapping("/amount")
public class AmountController {
    
    @Autowired AmountService amountService;
    
    @PostMapping("add")
    public Object add(Amount amount) throws Exception {
        System.out.println(amount);
        amountService.add(amount);
        
        HashMap<String,Object> result = new HashMap<>();
        result.put("status", "success");
        return result;
    }
    
    @GetMapping("update")
    public Object update(
            Amount amount,
            HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        
        Member member = (Member) session.getAttribute("loginUser");
        
        int memberNo = member.getNo();
        
        System.out.println(amount);
        amountService.update(amount);
        
        HashMap<String,Object> result = new HashMap<>();
        result.put("status", "success");
        return result;
    }
    
    @GetMapping("delete")
    public Object delete(Amount amount, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        
        Member member = (Member) session.getAttribute("loginUser");
        
        int memberNo = member.getNo();
        
        System.out.println(amount);
        amountService.delete(amount, memberNo);
        
        HashMap<String,Object> result = new HashMap<>();
        result.put("status", "success");
        return result;
    }
    
}
