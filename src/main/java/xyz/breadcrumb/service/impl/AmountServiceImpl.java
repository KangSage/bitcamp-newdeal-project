package xyz.breadcrumb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.breadcrumb.domain.Amount;
import xyz.breadcrumb.domain.DayHistory;
import xyz.breadcrumb.repository.AmountRepository;
import xyz.breadcrumb.service.AmountService;

import java.util.List;

@Service
public class AmountServiceImpl implements AmountService {
    
    @Autowired
    AmountRepository amountRepository;
    
    @Override
    public int add(Amount amount) {
        return amountRepository.insert(amount);
    }

/*    @Override
    public List<Amount> list(int memberNo) {
        return amountRepository.selectList(memberNo);
    }*/

    @Override
    public List<DayHistory> list2(int memberNo) {
        return amountRepository.selectList2(memberNo);
    }

    @Override
    public int update(Amount amount) {
        return amountRepository.update(amount);
    }

    @Override
    public int delete(Amount amount, int memberNo) {
        return amountRepository.delete(amount, memberNo);
        
    }
}
