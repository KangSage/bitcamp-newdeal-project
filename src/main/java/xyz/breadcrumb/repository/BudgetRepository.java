package xyz.breadcrumb.repository;

import java.util.Map;
import java.util.HashMap;

import xyz.breadcrumb.domain.Budget;

public interface BudgetRepository {

    /*Budget findBy(Map<String, Object> params);*/
    
    Budget findByMonthAndMemberNo(Map<String, Object> params);

    int insert(Budget budget);

    int update(Budget budget);

    int delete(HashMap<String, Object> params);

    
    
}
