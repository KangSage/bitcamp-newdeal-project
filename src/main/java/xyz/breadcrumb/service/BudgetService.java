package xyz.breadcrumb.service;

import xyz.breadcrumb.domain.Budget;


public interface BudgetService {

    /*Budget selectOne(int no, String month);*/

    Budget get(String month, int userNo);

    int add(Budget budget);

    int update(Budget budget);

    int delete(int budgetNo, int userNo );
    
}
