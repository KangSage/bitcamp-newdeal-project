package xyz.breadcrumb.service;

import xyz.breadcrumb.domain.Amount;
import xyz.breadcrumb.domain.DayHistory;

import java.util.List;

public interface AmountService {
    int add(Amount amount);
    List<DayHistory> list(int memberNo);
    int update(Amount amount);
    int delete(Amount amount, int memberNo);

}
