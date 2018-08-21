package xyz.breadcrumb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.breadcrumb.domain.Member;
import xyz.breadcrumb.repository.MemberRepository;
import xyz.breadcrumb.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberRepository memberRepository;

    @Override
    public int add(Member member) {
        return memberRepository.insert(member);
    }

}
