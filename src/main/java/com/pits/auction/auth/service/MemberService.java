package com.pits.auction.auth.service;

import com.pits.auction.auth.entity.Member;

import java.util.List;

public interface MemberService {

    public List<Member> getMemberList();

    public Member AdminEditMember();

    Member getMemberDetail(Long id);
}
