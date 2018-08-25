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
    public List<DayHistory> list(int memberNo) {
        return amountRepository.selectList(memberNo);
    }
    
    @Override
    public int add(Amount amount) {
        return amountRepository.insert(amount);
    }
    
    public Amount get(int amntNo, int memberNo) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("amntNo", amntNo);
        params.put("memberNo", memberNo);
        return amountRepository.findByAmntNoAndMemberNo(params);
    }
    
    @Override
    public int update(Amount amount) {
        return amountRepository.update(amount);
    }

    @Override
    public int delete(int amntNo, int memberNo) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("amntNo", amntNo);
        params.put("memberNo", memberNo);
        return amountRepository.delete(params);
    }


}
