package com.pits.auction.auth.service;


import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    public List<Member> getMemberList(){
        List<Member> memberList=memberRepository.findAll();
        return memberList;
    }

    @Override
    public Member AdminEditMember() {
        return null;
    }


    public Member getMemberDetail(Long id){
        Optional<Member> member=memberRepository.findById(id);
        if(member.isPresent()){
            return member.get();
        }
        return null;
    }

    public List<Member> AdminEditMember(Long id){
    Optional<Member> optionalMember = memberRepository.findById(id);
    if (optionalMember.isPresent()) {
        Member member = optionalMember.get();
        member.setPassword(member.getPassword());
        member.setPhoneNumber(member.getPhoneNumber());
        member.setBalance(member.getBalance());
        memberRepository.save(member);

    }
        return null;
    }



}
