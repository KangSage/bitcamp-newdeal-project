package xyz.breadcrumb.repository;

import xyz.breadcrumb.domain.Amount;

public interface AmountRepository {
    
    int insert(Amount amount);
    
    int update(Amount amount);
    
    int delete(Amount amount, int memberNo);
    
}
