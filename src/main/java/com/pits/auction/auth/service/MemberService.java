package com.pits.auction.auth.service;

import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.entity.Member;

import java.util.List;

public interface MemberService {

    public List<Member> getMemberList();
    public List<Member> getMemberYList();
    public Member getMemberDetail(Long id);

    public void deleteMember(Long id);

    public void deleteMembers(List<Long> ids);

    public void AdminEditMember(MemberDTO memberDTO, Long id);


}
