package com.pits.auction.auth.controller;

import com.pits.auction.auctionBoard.dto.MusicAuctionDTO;
import com.pits.auction.auctionBoard.service.BiddingService;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import com.pits.auction.auctionBoard.service.WishListService;
import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.repository.MemberRepository;
import com.pits.auction.auth.service.MemberService;
import com.pits.auction.auth.validation.MemberEditValidator;
import com.pits.auction.global.exception.InsufficientBalanceException;
import com.pits.auction.global.exception.PhoneNumberDuplicateException;
import com.pits.auction.global.upload.ImageUpload;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class MyPageController {

    private final MemberService memberService;
    private final BiddingService biddingService;
    private final MusicAuctionService musicAuctionService;
    private final WishListService wishListService;
    private final MemberRepository memberRepository;
    private final ImageUpload imageUpload;

    /* 유저 전체 리스트 - 마이페이지에서는 필요없으나 테스트를 위해 작성 */
    @GetMapping("/userlist")
    public String getUserList(Model model) {
        
        model.addAttribute("userInfoList", memberService.findAllActiveMembers());
        
        return "/myPage/plMyPage";
    }


    /* 마이페이지 (id) */
    @GetMapping("/userinfo")
    public String getUserInfo(Model model) {

        // 시큐리티 적용시 수정 해야함
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null;
        String email = null;
        MemberDTO userInfo = null;
        if (authentication != null && authentication.isAuthenticated()) {
            email = authentication.getName();
            Optional<Member> optionalMember = memberRepository.findByEmail(email);

            if (optionalMember.isPresent()) {
                userId = optionalMember.get().getId();
            }
        }

        // 회원 정보
        userInfo = memberService.getUserInfo(userId);
        model.addAttribute("userInfo",userInfo);

        // 입찰중인 경매물품 - 마지막에 입찰한 물품 하나
        MusicAuctionDTO auction = musicAuctionService.getLastBiddingAuction(userInfo.getNickname());

        // 입찰중인 경매물품이 존재할 경우
        if (auction != null) {
            // auction 객체를 받아온 경우에 수행할 로직
            model.addAttribute("auction", auction);

            // 해당 물품 입찰 최고가
            model.addAttribute("maxPrice",biddingService.getMaxBidPriceForAuction(auction.getId()));
            model.addAttribute("myPrice",musicAuctionService.findLastBidPriceByNickname(userInfo.getNickname()));
        }

        // 찜목록
        model.addAttribute("wishLists", wishListService.getMusicAuctionsByEmail(userInfo.getEmail()));

        return "/myPage/userRead";
    }


    /* 특정 유저 수정 폼 */
    @GetMapping("/useredit")
    public String formUserEdit(Model model,
                               MemberEditValidator memberEditValidator) {

        // 시큐리티 적용시 수정 해야함
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = null;
        Member userInfo = null;
        if (authentication != null && authentication.isAuthenticated()) {
            email = authentication.getName();
            Optional<Member> optionalMember = memberRepository.findByEmail(email);

            if (optionalMember.isPresent()) {
                userInfo = optionalMember.get();
            }
        }

        MemberDTO memberDTO = memberService.entityToDTO(userInfo);

        model.addAttribute("userInfo", memberDTO);

        return "/myPage/userEdit";
    }


    /* 특정 유저 수정 작업 */
    @PostMapping("/useredit")
    public String funcUserEdit(@ModelAttribute @Valid MemberEditValidator memberEditValidator,
                               BindingResult bindingResult,
                               @RequestParam("image") MultipartFile imageFile,
                               Model model) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = null;
        Member member = null;
        Long userId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            email = authentication.getName();
            Optional<Member> optionalMember = memberRepository.findByEmail(email);

            if (optionalMember.isPresent()) {
                member = optionalMember.get();
                userId = member.getId();
            }
        }

        MemberDTO userInfo = memberService.entityToDTO(member);

        // 비밀번호 유효성 검사 에러가 있는지 확인
        if (bindingResult.hasErrors()) {

            model.addAttribute("userInfo", userInfo);
            if (bindingResult.hasFieldErrors("password")) {
                bindingResult.reject("password", bindingResult.getFieldError("password").getDefaultMessage());
            }

            if (bindingResult.hasFieldErrors("phoneNumber")) {
                bindingResult.reject("phoneNumber", bindingResult.getFieldError("phoneNumber").getDefaultMessage());
            }

            return "/myPage/userEdit";      // 유효성 검사 에러가 있을 경우 수정 페이지로 다시 돌아감
        }

        if (!imageFile.isEmpty()){   // 파일이 있을 경우에만 파일 업로드 진행
            // 이미지 저장과 경로 DTO에 저장
            userInfo.setMemberImage(imageUpload.uploadImage(imageFile, userId , "member"));
        }

        // 중복된 정보가 없으면 업데이트
        if (memberService.duplicatePhoneNumber(memberEditValidator.getId(),memberEditValidator.getPhoneNumber())){
            throw new PhoneNumberDuplicateException(memberEditValidator.getPhoneNumber());
        } else {
            userInfo.setPhoneNumber(memberEditValidator.getPhoneNumber());
        }

        userInfo.setPassword(memberEditValidator.getPassword());

        memberService.updateUserInfo(userInfo);

        return "redirect:/mypage/userinfo";
    }


    /* 회원 삭제 요청 */
    @GetMapping("/userdelete")
    public String requestUserDelete(@RequestParam Long userId) {

        memberService.requestUserDelete(userId);
        return "redirect:/mypage/userinfo";
    }


    /* 입찰 내역 */
    @GetMapping("/bidding-history")
    public String getBiddingHistory() {

        return "";
    }


    /* 해당 회원 잔고를 보기위한 기능 - 현재 필요없음 */
    @Transactional
    @GetMapping("/balance")
    public String getBalance(@RequestParam Long userId, Model model) {

        model.addAttribute("userInfo", memberService.getUserInfo(userId));

        return "/myPage/userBalance";
    }


    /* 입금 폼 (서브창을 띄울때 사용) */
    @GetMapping("/depositform")
    public String depositFrom(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = null;
        Member member = null;
        Long userId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            email = authentication.getName();
            Optional<Member> optionalMember = memberRepository.findByEmail(email);

            if (optionalMember.isPresent()) {
                member = optionalMember.get();
                userId = member.getId();
            }
        }
        model.addAttribute("userInfo", memberService.getUserInfo(userId));
        return "/myPage/depositPopup";
    }

    /* 출금 폼 (서브창을 띄울때 사용) */
    @GetMapping("/withdrawform")
    public String withdrawFrom(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = null;
        Member member = null;
        Long userId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            email = authentication.getName();
            Optional<Member> optionalMember = memberRepository.findByEmail(email);

            if (optionalMember.isPresent()) {
                member = optionalMember.get();
                userId = member.getId();
            }
        }


        model.addAttribute("userInfo", memberService.getUserInfo(userId));
        return "/myPage/withdrawPopup";
    }

    /* 입/출금 기능 (입금인지 출금인지 action변수에 받아와 하나의 메서드에서 두 기능을 구현) */
    @PostMapping("/balance")
    @ResponseBody
    public String transactionBalance(
            @RequestParam String action,
            @RequestParam Long balance,
            Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = null;
        Member member = null;
        Long userId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            email = authentication.getName();
            Optional<Member> optionalMember = memberRepository.findByEmail(email);

            if (optionalMember.isPresent()) {
                member = optionalMember.get();
                userId = member.getId();
            }
        }

        try {
            if ("deposit".equals(action)) {
                return memberService.addBalance(userId, balance);
            } else if ("withdraw".equals(action)) {
                return memberService.minusBalance(userId, balance);
            }
        } catch (InsufficientBalanceException e) {
            return "실패";
        } catch (IllegalArgumentException e) {
            return "실패";       // 다른 예외 페이지로 이동
        }

        return "실패";
    }


}