package xyz.breadcrumb.repository;

import java.util.HashMap;
import java.util.List;

import xyz.breadcrumb.domain.Amount;
import xyz.breadcrumb.domain.DayHistory;

public interface AmountRepository {
    
    List<DayHistory> selectList(int userNo);
    
    Amount findByAmountNoAndMemberNo(HashMap<String, Object> params);
    
    int insert(Amount amount);
    
    int update(Amount amount);
    
    int delete(HashMap<String, Object> params);
}
