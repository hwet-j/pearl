package com.pits.auction.auth.service;

import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.entity.Member;

import java.util.List;

public interface MemberService {

    List<MemberDTO> getUserInfo();
}
