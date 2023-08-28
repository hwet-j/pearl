package com.pits.auction.auth.service;


import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.repository.MemberRepository;
import com.pits.auction.global.exception.InsufficientBalanceException;
import com.pits.auction.global.exception.PhoneNumberDuplicateException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    
    /* 특정 유저 정보 수정 */
    @Override
    public boolean updateUserInfo(MemberDTO memberDTO) {
        // 업데이트할 회원의 ID를 기반으로 해당 회원 정보 조회
        Optional<Member> memberOptional = memberRepository.findById(memberDTO.getId());

        if (memberOptional.isPresent()) {   // 유저 조회에 성공하면 수정작업 진행
            Member existingMember = memberOptional.get();

            if (memberDTO.getPhoneNumber() != null && !memberDTO.getPhoneNumber().isEmpty()) {
                if (memberRepository.findByPhoneNumber(memberDTO.getPhoneNumber()).isPresent()){
                    throw new PhoneNumberDuplicateException(memberDTO.getPhoneNumber());
                }
                existingMember.setPhoneNumber(memberDTO.getPhoneNumber());
            }

            if (memberDTO.getPassword() != null && !memberDTO.getPassword().isEmpty()) {
                existingMember.setPassword(memberDTO.getPassword());
            }

            if (memberDTO.getMemberImage() != null && !memberDTO.getMemberImage().isEmpty()) {
                existingMember.setMemberImage(memberDTO.getMemberImage());
            }

            memberRepository.save(existingMember);
            return true;
        }
        return false;
    }


    /* 회원 삭제 요청하기 (회원은 DB에서 삭제하지 않음) */
    @Override
    public boolean requestUserDelete(Long userId) {
        Optional<Member> memberOptional = memberRepository.findById(userId);

        if (memberOptional.isPresent()) {
            Member existingMember = memberOptional.get();
            existingMember.setWithdrawalRequested(true);    // 삭제 요청 true로 변경
            
            memberRepository.save(existingMember);
            return true;
        }
        return false;
    }


    @Override
    public Long getBalance(String nickname) {
        Optional<Member> memberOptional = memberRepository.findByNickname(nickname);

        if (memberOptional.isPresent()) {
            return memberOptional.get().getBalance();
        }

        return null;
    }


    @Override
    @Transactional
    public void addBalance(Long userId, Long amount) {
        Member user = memberRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + userId));

        // 음수가 들어오면 예외처리
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid withdrawal amount: " + amount);
        }

        Long newBalance = user.getBalance() + amount;

        user.setBalance(newBalance);

        memberRepository.save(user);
    }


    @Override
    @Transactional
    public void minusBalance(Long userId, Long amount) {
        Member user = memberRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + userId));

        // 음수가 들어오면 예외처리
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid withdrawal amount: " + amount);
        }
        // 가진 금액보다 큰 금액이 들어오면 예외처리
        if (user.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal");
        }

        Long newBalance = user.getBalance() - amount;
        user.setBalance(newBalance);

        memberRepository.save(user);
    }


    @Override
    public boolean duplicatePhoneNumber(String phoneNumber){
        return memberRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    @Override
    public List<MemberDTO> findAllActiveMembers() {

        List<Member> members =  memberRepository.findAllActiveMembers();
        return members.stream()
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .collect(Collectors.toList());
    }


}
