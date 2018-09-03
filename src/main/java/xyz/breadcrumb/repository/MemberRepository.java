package xyz.breadcrumb.repository;

import xyz.breadcrumb.domain.Member;

import java.util.Map;

public interface MemberRepository {
    int insert(Member member);
    Member findByEmailAndPassword(Map<String, Object> params);
    int update(Map<String, Object> params);
    int delete(Map<String, Object> params);
}
