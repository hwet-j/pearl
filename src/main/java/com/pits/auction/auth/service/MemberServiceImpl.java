package com.pits.auction.auth.service;


import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;


    @Override
    public Member findAnyMember() {
        return memberRepository.findAll().stream().findFirst().orElse(null);
    }
}
