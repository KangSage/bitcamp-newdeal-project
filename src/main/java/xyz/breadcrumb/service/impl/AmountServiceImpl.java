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
    public List<DayHistory> list(int userNo, String month) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userNo", userNo);
        params.put("month", month);
        return amountRepository.selectList(params);
    }

    @Override
    public Integer getTotalAmount(int userNo, String amountType, String month) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userNo", userNo);
        params.put("month", month);
        params.put("amountType", amountType);
        return amountRepository.findTotalByAmountTypeAndMonth(params);
    }

    @Override
    public int add(Amount amount) {
        int count = amountRepository.insert(amount);
        amountRepository.insertContents(amount);
        return count;
    }
    
    public Amount get(int amountNo, int userNo) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("amountNo", amountNo);
        params.put("userNo", userNo);
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
        params.put("userNo", userNo);
        return amountRepository.delete(params);
    }


}
