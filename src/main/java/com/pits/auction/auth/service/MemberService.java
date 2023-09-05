package com.pits.auction.auth.service;

import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.List;

public interface MemberService {
//    Member findAnyMember();

    List<MemberDTO> getUserList();

    MemberDTO getUserInfo(Long id);

    boolean updateUserInfo(MemberDTO memberDTO);

    boolean requestUserDelete(Long userId);

    Long getBalance(String nickname);

    void addBalance(Long userId, Long amount);

    void minusBalance(Long userId, Long amount);

    boolean duplicatePhoneNumber(Long id, String phoneNumber);

    List<MemberDTO> findAllActiveMembers();

    public Page<Member> getMemberList(Pageable pageable);
    public Page<Member> getMemberYList(Pageable pageable);
    public Member getMemberDetail(Long id);

    public void deleteMember(Long id);

    public void deleteMembers(List<Long> ids);

    public void AdminEditMember(MemberDTO memberDTO, Long id);



}