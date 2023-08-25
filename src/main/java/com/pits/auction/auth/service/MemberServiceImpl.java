package com.pits.auction.auth.service;


import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;



    /* 유저 전체 목록 */
    @Override
    public List<MemberDTO> getUserList() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .collect(Collectors.toList());
    }

    /* 유저 한명의 정보 (id) */
    @Override
    public MemberDTO getUserInfo(Long id) {
        Optional<Member> existingMember = memberRepository.findById(id);
        if (existingMember.isPresent()) {
            Member member = existingMember.get();
            return modelMapper.map(member, MemberDTO.class);
        }
        return null;    // 추후에 에러 기능 구현
    }


    @Override
    public boolean  updateUserInfo(MemberDTO memberDTO) {
        // 업데이트할 회원의 ID를 기반으로 해당 회원 정보 조회
        Optional<Member> memberOptional = memberRepository.findById(memberDTO.getId());

        if (memberOptional.isPresent()) {
            Member existingMember = memberOptional.get();

            modelMapper.map(memberDTO, existingMember);

            memberRepository.save(existingMember); // 엔티티 업데이트
            return true;
        }
        return false;
    }


    @Override
    public boolean requestUserDelete(MemberDTO memberDTO) {
        Optional<Member> memberOptional = memberRepository.findById(memberDTO.getId());

        if (memberOptional.isPresent()) {
            Member existingMember = memberOptional.get();

            modelMapper.map(memberDTO, existingMember);

            memberRepository.save(existingMember);
            return true;
        }
        return false;
    }


}
