package xyz.breadcrumb.web;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.breadcrumb.domain.Amount;
import xyz.breadcrumb.domain.DayHistory;
import xyz.breadcrumb.domain.Member;
import xyz.breadcrumb.service.AmountService;

@RestController
@RequestMapping("/amount")
public class AmountController {
    
    @Autowired AmountService amountService;
    
    @CrossOrigin()
    @RequestMapping("list")
    public Object list(HttpServletRequest httpRequest) throws Exception {
        HttpSession session = httpRequest.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");


        HashMap<String, Object> data = new HashMap<>();
        try {
            List<DayHistory> list = amountService.list(loginUser.getNo());
            data.put("list", list);
        } catch (Exception e) {
            data.put("status", "fail");
            data.put("message", e.getMessage());
        }
        return data;
    }
    
    @GetMapping("{no}")
    public Object get(
            @PathVariable int no, HttpSession session) {
        Member loginUser = (Member)session.getAttribute("loginUser");

        HashMap<String,Object> result = new HashMap<>();
        try {
            Amount amount = amountService.get(no, loginUser.getNo());
            result.put("status", "success");
            result.put("data", amount);
        } catch (Exception e) {
            result.put("status", "fail");
            result.put("message", e.getMessage());
        }
        return result;
    }    
    
    @PostMapping("add")
    public Object add(
            Amount amount,
            HttpServletRequest httpRequest) {
        
        HttpSession session = httpRequest.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        HashMap<String,Object> result = new HashMap<>();
        try {
            amount.setMemberNo(loginUser.getNo());
            amountService.add(amount);
            result.put("status", "success");
        } catch (Exception e) {
            result.put("status", "fail");
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    
    @PostMapping("update")
    public Object update(
            Amount amount,
            HttpServletRequest httpRequest) {
        
        HttpSession session = httpRequest.getSession();
        
        Member loginUser = (Member) session.getAttribute("loginUser");


        HashMap<String, Object> result = new HashMap<>();
        try {
            amount.setMemberNo(loginUser.getNo());
            amountService.update(amount);
            result.put("status", "success");
        } catch (Exception e) {
            result.put("status", "fail");
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @PostMapping("delete")
    public Object delete(
            int no,
            HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        HashMap<String,Object> data = new HashMap<>();
        try {
            amountService.delete(no, loginUser.getNo());
            data.put("status", "success");
            return data;
        } catch (Exception e) {
            data.put("status", "fail");
            data.put("message", e.getMessage());
        }
        return data;
    }
}
