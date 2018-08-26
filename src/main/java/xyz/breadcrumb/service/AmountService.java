package xyz.breadcrumb.service;

import java.util.List;

import xyz.breadcrumb.domain.Amount;
import xyz.breadcrumb.domain.DayHistory;

public interface AmountService {
    
    List<DayHistory> list(int userNo);
    
    Amount get(int amountNo, int userNo);
    
    int add(Amount amount);
    
    int update(Amount amount);
    
    int delete(int amountNo, int userNo);

}
