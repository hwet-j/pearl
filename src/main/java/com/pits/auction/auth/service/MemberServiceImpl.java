package com.pits.auction.auth.service;


import com.pits.auction.auth.dto.MemberDTO;
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

    public Member getMemberDetail(Long id){
        Optional<Member> member=memberRepository.findById(id);
        if(member.isPresent()){
            return member.get();
        }
        return null;
    }
    public void deleteMember(Long id){
        Optional<Member> member=memberRepository.findById(id);
        System.out.println("id="+id);
        Long deleteId= member.get().getId();
        memberRepository.deleteById(deleteId);

    }

    public void deleteMembers(List<Long> ids) {
        for (Long id : ids) {
            memberRepository.deleteById(id);
        }
    }

    @Override
    public void AdminEditMember(MemberDTO memberDTO, Long id){
    Optional<Member> optionalMember = memberRepository.findById(id);
    if (optionalMember.isPresent()) {
        Member member = optionalMember.get();
        member.setPassword(memberDTO.getPassword());
        member.setPhoneNumber(memberDTO.getPhoneNumber());
        member.setWithdrawalRequested(memberDTO.getWithdrawalRequested());
        memberRepository.save(member);
    }
    }

    @Override
    public Member findAnyMember() {
        return memberRepository.findAll().stream().findFirst().orElse(null);
    }
}
