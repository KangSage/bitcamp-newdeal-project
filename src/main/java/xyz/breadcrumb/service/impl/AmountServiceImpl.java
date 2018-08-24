package xyz.breadcrumb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.breadcrumb.domain.Amount;
import xyz.breadcrumb.repository.AmountRepository;
import xyz.breadcrumb.service.AmountService;

@Service
public class AmountServiceImpl implements AmountService {

    @Autowired
    AmountRepository amountRepository;
    
    @Override
    public int add(Amount amount) {
        return amountRepository.insert(amount);
    }
    
}
