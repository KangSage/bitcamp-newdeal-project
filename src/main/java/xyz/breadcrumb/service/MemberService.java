package xyz.breadcrumb.service;

import xyz.breadcrumb.domain.Member;

public interface MemberService {
    String selectName(int no);
    int add(Member member);
    int update(int no, String oldPassword, String newPassword);
    int delete(int userNo, String email, String password);
}
