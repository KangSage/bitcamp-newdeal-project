package xyz.breadcrumb.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.breadcrumb.domain.Amount;
import xyz.breadcrumb.domain.DayHistory;
import xyz.breadcrumb.repository.AmountRepository;
import xyz.breadcrumb.service.AmountService;

@Service
public class AmountServiceImpl implements AmountService {
    
    @Autowired
    AmountRepository amountRepository;
    
    @Override
    public List<DayHistory> list(int userNo) {
        return amountRepository.selectList(userNo);
    }
    
    @Override
    public int add(Amount amount) {
        return amountRepository.insert(amount);
    }
    
    public Amount get(int amountNo, int userNo) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("amountNo", amountNo);
        params.put("memberNo", userNo);
        return amountRepository.findByAmountNoAndMemberNo(params);
    }
    
    @Override
    public int update(Amount amount) {
        return amountRepository.update(amount);
    }

    @Override
    public int delete(int amountNo, int userNo) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("amountNo", amountNo);
        params.put("memberNo", userNo);
        return amountRepository.delete(params);
    }


}
