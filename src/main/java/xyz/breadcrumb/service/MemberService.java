package xyz.breadcrumb.service;

import xyz.breadcrumb.domain.Member;

public interface MemberService {
    int add(Member member);
    int update(int no, String oldPassword, String newPassword);
    int delete(int userNo, String email, String password);
}
