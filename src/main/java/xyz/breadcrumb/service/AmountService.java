package xyz.breadcrumb.service;

import xyz.breadcrumb.domain.Amount;

public interface AmountService {

    int add(Amount amount);
    
    int update(Amount amount);
    
    int delete(Amount amount, int memberNo);

}
