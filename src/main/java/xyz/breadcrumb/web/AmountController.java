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
    
    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("list")
    public Object list(HttpServletRequest httpRequest) throws Exception {
        HttpSession session = httpRequest.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");
        
        int userNo = loginUser.getNo();
        /*List<Amount> list = amountService.list(memberNo);*/
        List<DayHistory> list = amountService.list(userNo);
        HashMap<String,Object> data = new HashMap<>();
        data.put("list", list);
        return data;
    }
    
    @GetMapping("{no}")
    public Object get(
            @PathVariable int no, HttpSession session) {
        
        Member loginUser = 
                (Member)session.getAttribute("loginUser");
        
        Amount amount = 
                amountService.get(no, loginUser.getNo());
        
        HashMap<String,Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("data", amount);
        return result;
    }    
    
    @PostMapping("add")
    public Object add(Amount amount) throws Exception {
        System.out.println(amount);
        amountService.add(amount);
        
        HashMap<String,Object> result = new HashMap<>();
        result.put("status", "success");
        return result;
    }
    
    
    @PostMapping("update")
    public Object update(
            Amount amount,
            HttpServletRequest httpRequest) {
        
        HttpSession session = httpRequest.getSession();
        
        Member loginUser = (Member) session.getAttribute("loginUser");
        
        System.out.println(loginUser);
        
        amount.setMno(loginUser.getNo());
        System.out.println(amount);
        
        amountService.update(amount);
        
        HashMap<String,Object> result = new HashMap<>();
        result.put("status", "success");
        return result;
    }
    
    @PostMapping("delete")
    public Object delete(
            int no,
            HttpServletRequest httpRequest) {

        System.out.println(no);
        
        HttpSession session = httpRequest.getSession();
        
        Member loginUser = (Member) session.getAttribute("loginUser");
        
        amountService.delete(no, loginUser.getNo());
        
        HashMap<String,Object> data = new HashMap<>();
        data.put("status", "success");
        return data;

    }
    
    /*    @CrossOrigin
    @RequestMapping("list2")
    public Object list2(HttpServletRequest httpRequest) throws Exception {
     *//*HttpSession session = httpRequest.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        int memberNo = loginUser.getNo();*//*
        List<DayHistory> list = amountService.list2(11);

        HashMap<String,Object> data = new HashMap<>();
        data.put("list", list);
        return data;
    }*/
}
