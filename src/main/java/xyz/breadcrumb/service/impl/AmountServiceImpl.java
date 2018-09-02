package xyz.breadcrumb.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.breadcrumb.domain.Amount;
import xyz.breadcrumb.domain.DayHistory;
import xyz.breadcrumb.repository.AmountRepository;
import xyz.breadcrumb.repository.ContentsRepository;
import xyz.breadcrumb.service.AmountService;

@Service
public class AmountServiceImpl implements AmountService {
    
    @Autowired
    AmountRepository amountRepository;

    @Autowired
    ContentsRepository contentsRepository;

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
        contentsRepository.insert(amount);
        return count;
    }

    //
    public Amount get(int amountNo, int userNo) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("amountNo", amountNo);
        params.put("userNo", userNo);
        return amountRepository.findByAmountNoAndMemberNo(params);
    }

    // 기존에 있던 파일 이름을 얻어온다.
    public String get(int amountNo) {
        return contentsRepository.findByAmountNo(amountNo);
    }

    @Override
    public int update(Amount amount) {
        System.out.println(amount);
        int count = amountRepository.update(amount);
        System.out.println(amount);
        contentsRepository.update(amount);
        return count;
    }

    @Override
    public int delete(int amountNo, int userNo) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("amountNo", amountNo);
        params.put("userNo", userNo);
        contentsRepository.delete(amountNo);
        return amountRepository.delete(params);
    }


}
