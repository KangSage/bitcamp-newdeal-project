package xyz.breadcrumb.repository;

import xyz.breadcrumb.domain.Amount;

public interface ContentsRepository {
    int insert(Amount amount);
    int update(Amount amount);
    String findByAmountNo(int amountNo);
    int delete(int amountNo);
}
