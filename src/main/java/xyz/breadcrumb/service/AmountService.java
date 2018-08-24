package xyz.breadcrumb.service;

import xyz.breadcrumb.domain.Amount;
import xyz.breadcrumb.domain.DayHistory;

import java.util.List;

public interface AmountService {
    int add(Amount amount);
    /*List<Amount> list(int memberNo);*/
    List<DayHistory> list2(int memberNo);
    int update(Amount amount);
    int delete(Amount amount, int memberNo);

}
