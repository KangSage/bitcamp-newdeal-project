package xyz.breadcrumb.repository;

import xyz.breadcrumb.domain.Amount;
import xyz.breadcrumb.domain.DayHistory;

import java.util.List;

public interface AmountRepository {
    int insert(Amount amount);
    /*List<Amount> selectList(int memberNo);*/
    List<DayHistory> selectList2(int memberNo);
    int update(Amount amount);
    int delete(Amount amount, int memberNo);
}