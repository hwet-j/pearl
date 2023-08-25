package com.pits.auction.auth.service;

import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.entity.Member;

import java.util.List;

public interface MemberService {

    List<MemberDTO> getUserList();

    MemberDTO getUserInfo(Long id);

    boolean  updateUserInfo(MemberDTO memberDTO);

    boolean  requestUserDelete(MemberDTO memberDTO);
}
