package xyz.breadcrumb.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.breadcrumb.domain.Amount;
import xyz.breadcrumb.domain.DayHistory;

public interface AmountRepository {
    List<DayHistory> selectList(Map<String, Object> params);
    Amount findByAmountNoAndMemberNo(Map<String, Object> params);
    int insert(Amount amount);
    int insertContents(Amount amount);
    int update(Amount amount);
    int delete(Map<String, Object> params);
    Integer findTotalByAmountTypeAndMonth(HashMap<String, Object> params);
}
