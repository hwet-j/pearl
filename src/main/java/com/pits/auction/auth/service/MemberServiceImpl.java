package com.pits.auction.auth.service;


import com.pits.auction.auctionBoard.service.BiddingService;
import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final BiddingService biddingService;
    private final ModelMapper modelMapper;

    @Override
    public Page<Member> getMemberList(Pageable pageable){
        Page<Member> memberList=memberRepository.findAll(pageable);
        return memberList;
    }


    @Override
    public Page<Member> getMemberYList(Pageable pageable){
        Page<Member> memberYList=memberRepository.findByWithdrawalRequestedTrue(pageable);
        return memberYList;
    }

    @Override
    public Member getMemberDetail(Long id){
        Optional<Member> member=memberRepository.findById(id);
        if(member.isPresent()){
            return member.get();
        }
        return null;
    }


    @Override
    public Member getUser(String email){
        Optional<Member> member=memberRepository.findByEmail(email);
        if(member.isPresent()){
            return member.get();
        }
        return null;
    }

    @Override
    public void deleteMember(Long id){
        Optional<Member> member=memberRepository.findById(id);
        System.out.println("id="+id);
        Long deleteId= member.get().getId();
        memberRepository.deleteById(deleteId);

    }

    @Override
    public void deleteMembers(List<Long> ids) {
        for (Long id : ids) {
            memberRepository.deleteById(id);
        }
    }

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

            if (memberDTO.getPassword() != null && !memberDTO.getPassword().isEmpty()) {
                existingMember.setPassword(memberDTO.getPassword());
            }

            if (memberDTO.getPhoneNumber() != null && !memberDTO.getPhoneNumber().isEmpty()) {
                existingMember.setPhoneNumber(memberDTO.getPhoneNumber());
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


    /* 회원 잔고 가져오기 */
    @Override
    public Long getBalance(String nickname) {
        Optional<Member> memberOptional = memberRepository.findByNickname(nickname);

        if (memberOptional.isPresent()) {
            return memberOptional.get().getBalance();
        }

        return 0L;
    }

    @Override
    public Long getBalanceByEmail(String email) {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        if (memberOptional.isPresent()) {
            return memberOptional.get().getBalance();
        }

        return 0L;
    }

    /* 입금 Deposit */
    @Override
    @Transactional
    public String addBalance(Long userId, Long amount) {
        Member user = memberRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + userId));

        // 음수가 들어오면 예외처리
        if (amount <= 0L) {
            return "음수는 설정이 불가능합니다.";
        }

        if (amount > 500000L) {
            return "입금 금액은 50만원을 초과할 수 없습니다.";
        }

        Long newBalance = user.getBalance() + amount;

        user.setBalance(newBalance);

        memberRepository.save(user);
        return "Success";
    }

    /* 출금 Withdraw */
    @Override
    @Transactional
    public String minusBalance(Long userId, Long amount) {
        Member user = memberRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + userId));

        // 음수가 들어오면 예외처리
        if (amount <= 0) {
            return "음수는 설정이 불가능합니다.";
        }

        Long bidPrice = biddingService.totalPriceProcessingLastBiddingByNickname(user.getNickname());
        // 가진 금액보다 큰 금액이 들어오면 예외처리 및 입찰 정보도 계산하여 예외처리

        if ((user.getBalance() - bidPrice) < amount) {
            if (bidPrice == 0L){
                return "보유하신 금액이 부족합니다. " + (user.getBalance() - bidPrice) + "원까지 출금 가능합니다. ";
            } else {
                return "보유하신 금액이 부족합니다. 현재 입찰 진행중인 경매금액은 " + bidPrice + "원 입니다. " + (user.getBalance() - bidPrice) + "원까지 출금 가능합니다.";
            }
        }

        Long newBalance = user.getBalance() - amount;
        user.setBalance(newBalance);

        memberRepository.save(user);
        return "Success";
    }


    /* 전화번호 중복체크 - 자기자신의 id에 저장된 전화번호와는 동일해도 상관없음 */
    @Override
    public boolean duplicatePhoneNumber(Long id, String phoneNumber){
        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member.getPhoneNumber().equals(phoneNumber)){   // 이전 전화번호와 동일 (변경X)
                return false;
            } else {        // 변경한 정보가 이전 전화번호와 다르면 전화번호로 중복정보 있는지 확인
                return memberRepository.findByPhoneNumber(phoneNumber).isPresent();
            }
        }

        return true;    // 예외처리
    }


    /* 회원탈퇴 요청을 하지 않은 유저목록만 표시 */
    @Override
    public List<MemberDTO> findAllActiveMembers() {

        List<Member> members =  memberRepository.findAllActiveMembers();
        return members.stream()
                .map(member -> modelMapper.map(member, MemberDTO.class))
                .collect(Collectors.toList());
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


    public MemberDTO entityToDTO(Member member) {
        return modelMapper.map(member, MemberDTO.class);
    }
}