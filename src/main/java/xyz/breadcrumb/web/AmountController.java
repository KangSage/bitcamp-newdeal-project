package xyz.breadcrumb.web;

import java.text.SimpleDateFormat;
import java.util.*;

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
    public Object list(int monthOperator, HttpServletRequest httpRequest) throws Exception {
        HttpSession session = httpRequest.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        Calendar cal = new GregorianCalendar(Locale.KOREA);
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, monthOperator); //들어오는 숫자만큼 더 한다.

        SimpleDateFormat yearMonthFormat = new SimpleDateFormat("yyyy-MM");
        String selectDate = yearMonthFormat.format(cal.getTime());
        System.out.printf("만들어낸 날짜 => %s\n", selectDate);

        HashMap<String, Object> data = new HashMap<>();

        try {
            List<DayHistory> list = amountService.list(loginUser.getNo(), selectDate + '%');
            int totalIncomeAmount = amountService.getTotalAmount(loginUser.getNo(), "수입", selectDate + '%');
            int totalBudgetAmount = amountService.getTotalAmount(loginUser.getNo(), "지출", selectDate + '%');

            int monthlyTotalAmount = (totalIncomeAmount - totalBudgetAmount);

            data.put("list", list);
            data.put("selectDate",  selectDate);
            data.put("totalIncomeAmount",  totalIncomeAmount);
            data.put("totalBudgetAmount",  totalBudgetAmount);
            data.put("monthlyTotalAmount", monthlyTotalAmount);
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

        System.out.printf("loginUser => %s\n", loginUser);

        HashMap<String,Object> result = new HashMap<>();
        try {
            amount.setMemberNo(loginUser.getNo());
            System.out.printf("amount => %s\n", amount);
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
        } catch (Exception e) {
            data.put("status", "fail");
            data.put("message", e.getMessage());
        }
        return data;
    }
}
