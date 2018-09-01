package xyz.breadcrumb.repository;

import xyz.breadcrumb.domain.Amount;

public interface ContentsRepository {
    int insertContents(Amount amount);
    /*int update(Amount amount);
    int delete(Map<String, Object> params);*/
}
