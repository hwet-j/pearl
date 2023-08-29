package com.pits.auction.auth.service;

import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.entity.Member;

import java.util.List;

public interface MemberService {

    List<MemberDTO> getUserList();

    MemberDTO getUserInfo(Long id);

    boolean updateUserInfo(MemberDTO memberDTO);

    boolean requestUserDelete(Long userId);

    Long getBalance(String nickname);

    void addBalance(Long userId, Long amount);

    void minusBalance(Long userId, Long amount);

    boolean duplicatePhoneNumber(Long id, String phoneNumber);

    List<MemberDTO> findAllActiveMembers();


}
