package xyz.breadcrumb.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.breadcrumb.domain.Budget;
import xyz.breadcrumb.domain.Member;
import xyz.breadcrumb.service.AmountService;
import xyz.breadcrumb.service.BudgetService;

@RestController
@RequestMapping("/budget")
public class BudgetController {
    
     @Autowired
     BudgetService budgetService;
     
     @Autowired
     AmountService amountService;

     @GetMapping("list")
     public Object list(HttpSession session, int monthOperator) {

         Member loginUser = (Member)session.getAttribute("loginUser");
         
         Calendar cal = new GregorianCalendar(Locale.KOREA);
         
         int today = cal.get(Calendar.DATE);
         int lastDay = cal.getActualMaximum(Calendar.DATE);
         int restDay = lastDay - today;
         cal.setTime(new Date());
         cal.add(Calendar.MONTH, monthOperator); // 들어오는 숫자만큼 더 한다.

         SimpleDateFormat yearMonthFormat = new SimpleDateFormat("yyyy-MM");
         String selectDate = yearMonthFormat.format(cal.getTime());
         
         Budget budget = budgetService.get(selectDate, loginUser.getNo());
         
         HashMap<String, Object> result = new HashMap<>();
         if (budget != null) {
             Integer totalBudgetAmount =
                     amountService.getTotalAmount(
                             loginUser.getNo(), "지출", selectDate + '%');
             
             int amount = budget.getAmount();
             
             if (totalBudgetAmount != null) {
                 budget.setWithdraw(totalBudgetAmount);
                 int withdraw = budget.getWithdraw();
                 int restMoney = (amount - withdraw);
                 int percent = (int)((double) withdraw / (double) amount * 100);
                 int restAver = restMoney / restDay;
                 int totalGuess = withdraw/(today-1) * lastDay;
                 
                 result.put("restAver", restAver);
                 result.put("percent", percent);
                 result.put("restMoney", restMoney);
                 result.put("totalGuess", totalGuess);
             }
         } 
         
         try {
             
             result.put("status", "success");
             result.put("budget", budget);
             result.put("selectDate", selectDate);
             
         } catch (Exception e) {
             
             result.put("status", "fail");
             result.put("message",e.getMessage());
         }
             return result;
         
     }
     
     @GetMapping("{month}")
     public Object get(@PathVariable String month, HttpSession session) {
         Member loginUser = (Member)session.getAttribute("loginUser");
         
         Budget budget = budgetService.get(month, loginUser.getNo());
         
         HashMap<String, Object> result = new HashMap<>();
         
         int amount = budget.getAmount();
         int withdraw = budget.getWithdraw();
         
         int restMoney = (amount - withdraw);
         
         int percent = (int)((double) withdraw / (double) amount * 100);

         result.put("percent", percent);
         result.put("restMoney", restMoney);
         result.put("status", "success");
         result.put("data", budget);
         
         return result;
     }
     
     @PostMapping("add")
     public Object add(Budget budget, HttpServletRequest request) {
         
         System.out.printf("상태 %s", budget);
         
         HttpSession session = request.getSession();
         
         Member loginUser = (Member) session.getAttribute("loginUser");
         
         HashMap<String, Object> result = new HashMap<>();
         
         try{
             budget.setMemberNo(loginUser.getNo());
             
             Integer totalBudgetAmount = 
                     amountService.getTotalAmount(
                             loginUser.getNo(), "지출", budget.getMonth() + '%');
             
             if (totalBudgetAmount != null) {
                 budget.setWithdraw(totalBudgetAmount);
             } else {
                 budget.setWithdraw(0);
             }
             budgetService.add(budget);
             result.put("status", "success");
        } catch (Exception e) {
             result.put("status", "fail");
             result.put("message",e.getMessage());
             System.out.println(e.getMessage());
         }
         
         return result;
     }
     
     @PostMapping("update")
     public Object update(
             Budget budget, 
             HttpSession session) {
         
         Member loginUser = 
                 (Member)session.getAttribute("loginUser");
         
         budget.setMemberNo(loginUser.getNo());
         
         budgetService.update(budget);
         
         HashMap<String,Object> result = new HashMap<>();
         result.put("status", "success");
         return result;
     }
     
     @GetMapping("delete")
     public Object delete(int no, HttpSession session) {
         
         Member loginUser = 
                 (Member)session.getAttribute("loginUser");
         
         budgetService.delete(no, loginUser.getNo());
         
         HashMap<String,Object> result = new HashMap<>();
         result.put("status", "success");
         return result;
     }
     
     
     
}
