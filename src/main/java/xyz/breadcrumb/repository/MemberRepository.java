package xyz.breadcrumb.repository;

import java.util.Map;

import xyz.breadcrumb.domain.Member;

public interface MemberRepository {
    int insert(Member member);
    Member findByEmailAndPassword(Map<String, Object> params);
    int update(Map<String, Object> params);
    int delete(Map<String, Object> params);
    String findByNumber(int no);
}
