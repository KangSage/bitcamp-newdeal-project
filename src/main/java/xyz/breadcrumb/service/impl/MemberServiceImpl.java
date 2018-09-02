package xyz.breadcrumb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.breadcrumb.domain.Member;
import xyz.breadcrumb.repository.MemberRepository;
import xyz.breadcrumb.service.MemberService;

import java.util.HashMap;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberRepository memberRepository;

    @Override
    public int add(Member member) {
        return memberRepository.insert(member);
    }

    @Override
    public int update(int userNo, String oldPassword, String newPassword) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userNo", userNo);
        params.put("oldPassword", oldPassword);
        params.put("newPassword", newPassword);
        return memberRepository.update(params);
    }

    @Override
    public int delete(int userNo, String email, String password) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userNo", userNo);
        params.put("email", email);
        params.put("password", password);
        return memberRepository.delete(params);
    }
}
