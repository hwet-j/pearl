package com.pits.auction.auth.service;

import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.dto.MemberDTO;
import java.util.List;

public interface MemberService {
    Member findAnyMember();

    public List<Member> getMemberList();

    public Member getMemberDetail(Long id);

    public void deleteMember(Long id);

    public void deleteMembers(List<Long> ids);

    public void AdminEditMember(MemberDTO memberDTO, Long id);


}
