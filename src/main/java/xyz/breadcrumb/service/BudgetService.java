package xyz.breadcrumb.service;

import java.util.List;

import xyz.breadcrumb.domain.Budget;


public interface BudgetService {

    List<Budget> list(int no, String month);

    Budget get(int budgetNo, int userNo);

    int add(Budget budget);
    
}
