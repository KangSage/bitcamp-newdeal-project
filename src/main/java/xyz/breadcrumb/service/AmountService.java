package xyz.breadcrumb.service;

import java.util.List;

import xyz.breadcrumb.domain.Amount;
import xyz.breadcrumb.domain.DayHistory;

public interface AmountService {
    
    List<DayHistory> list(int memberNo);
    
    Amount get(int amntNo, int memberNo);
    
    int add(Amount amount);
    
    int update(Amount amount);
    
    int delete(int amntNo, int memberNo);

}
