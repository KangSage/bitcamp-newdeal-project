package xyz.breadcrumb.repository;

import java.util.Map;
import java.util.List;

import xyz.breadcrumb.domain.Budget;

public interface BudgetRepository {

    List<Budget> selectList(Map<String, Object> params);

    Budget findByBudgetNoAndMemberNo(Map<String, Object> params);

    int insert(Budget budget);

    
    
}
