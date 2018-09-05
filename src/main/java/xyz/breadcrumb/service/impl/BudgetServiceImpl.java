package xyz.breadcrumb.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.breadcrumb.domain.Budget;
import xyz.breadcrumb.repository.BudgetRepository;
import xyz.breadcrumb.service.BudgetService;

@Service
public class BudgetServiceImpl implements BudgetService{
    
    @Autowired
    BudgetRepository budgetRepository;
    
/*    @Override
    public Budget selectOne(int no, String month) {
        
        HashMap<String, Object> params = new HashMap<>();
        params.put("userNo", no);
        params.put("month", month);
        return budgetRepository.selectList(params);
    }*/

    @Override
    public Budget get(String month, int userNo) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("month", month);
        params.put("userNo", userNo);
        return budgetRepository.findByMonthAndMemberNo(params);
    }

    @Override
    public int add(Budget budget) {
        System.out.printf("BudgetServiceImpl  add -> budget = %s", budget);
        return budgetRepository.insert(budget);
    }

    @Override
    public int update(Budget budget) {
        System.out.println("ServiceBudget"+budget);
        return budgetRepository.update(budget);
    }

    @Override
    public int delete(int budgetNo, int userNo) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("budgetNo", budgetNo);
        params.put("userNo", userNo);
        return budgetRepository.delete(params);
    }
    
}
