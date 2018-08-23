package xyz.breadcrumb.repository;

import xyz.breadcrumb.domain.Member;

import java.util.HashMap;
import java.util.Map;

public interface MemberRepository {
    int insert(Member member);
    Member findByEmailAndPassword(Map<String, Object> params);
}