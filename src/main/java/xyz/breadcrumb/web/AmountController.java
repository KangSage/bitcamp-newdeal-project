package xyz.breadcrumb.web;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import xyz.breadcrumb.domain.Amount;


import xyz.breadcrumb.domain.DayHistory;
import xyz.breadcrumb.domain.Member;
import xyz.breadcrumb.service.AmountService;

import javax.lang.model.util.Elements;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

/*    @GetMapping("update")
    public Object update(
            Amount amount,
            HttpServletRequest request) throws Exception {
*//*        HttpSession session = request.getSession();
        
        Member member = (Member) session.getAttribute("loginUser");
        
        int memberNo = member.getNo();*//*
        
        System.out.println(amount);
        amountService.update(amount);
        
        HashMap<String,Object> result = new HashMap<>();
        result.put("status", "success");
        return result;
    }*/
    
    @GetMapping("delete")
    public Object delete(Amount amount, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();

        Member member = (Member) session.getAttribute("loginUser");

        int memberNo = member.getNo();

        System.out.println(amount);
        System.out.println(memberNo);
        amountService.delete(amount, memberNo);

        HashMap<String, Object> result = new HashMap<>();
        result.put("status", "success");
        return result;

    }
}
