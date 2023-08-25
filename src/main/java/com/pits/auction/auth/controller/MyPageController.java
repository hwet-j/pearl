package com.pits.auction.auth.controller;

import com.pits.auction.auctionBoard.service.BiddingService;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.service.MemberService;
import com.pits.auction.global.upload.AudioUpload;
import com.pits.auction.global.upload.ImageUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;
    private final BiddingService biddingService;
    private final MusicAuctionService musicAuctionService;
    private final ImageUpload imageUpload;
    private final AudioUpload audioUpload;


    @GetMapping("/userlist")
    public String getUserList(Model model) {
        
        // 테스트를 위해 진행중.. 모든 회원 정보를 가져와 거기서 선택해 특정 회원을 다루기 위해
        model.addAttribute("userInfoList", memberService.getUserList());
        
        return "/myPage/plMyPage";
    }

    @GetMapping("/userinfo")
    public String getUserInfo(@RequestParam("userId") Long userId, Model model) {

        model.addAttribute("userInfo", memberService.getUserInfo(userId));

        return "/myPage/userRead";
    }

    @GetMapping("/useredit")
    public String formUserEdit(@RequestParam("userId") Long userId, Model model) {
        MemberDTO userInfo = memberService.getUserInfo(userId);
        model.addAttribute("userInfo", userInfo);
        return "/myPage/userEdit"; // userEdit.html 템플릿으로 이동
    }

    @PostMapping("/useredit")
    public String funcUserEdit(@ModelAttribute MemberDTO memberDTO,
                               @RequestParam("image") MultipartFile imageFile,
                               @RequestParam("audio") MultipartFile audioFile) {

        MemberDTO userInfo = memberService.getUserInfo(memberDTO.getId());

        if (!imageFile.isEmpty()){   // 파일이 있을 경우에만 파일 업로드 진행
            // 이미지 저장과 경로 DTO에 저장
            userInfo.setMemberImage(imageUpload.uploadImage(imageFile));
        }
        userInfo.setPassword(memberDTO.getPassword());
        userInfo.setPhoneNumber(memberDTO.getPhoneNumber());

        // 노래 업로드 테스트
        if (!audioFile.isEmpty()) {
            audioUpload.uploadAudio(audioFile);
        }

        memberService.updateUserInfo(userInfo); // 회원 정보 업데이트 로직 구현 필요
        return "redirect:/mypage/userlist"; // 회원 목록 페이지로 이동
    }


    @GetMapping("/userdelete")
    public String requestUserDelete(@RequestParam Long userId) {

        MemberDTO memberDTO = memberService.getUserInfo(userId);
        memberDTO.setWithdrawalRequested(true);
        memberService.updateUserInfo(memberDTO); // 회원 정보 업데이트 로직 구현 필요
        return "redirect:/mypage/userlist"; // 회원 목록 페이지로 이동
    }


    @GetMapping("/musictest")
    public String musicTest(Model model) {
        String audioFileName = "a0b88416-6ca4-41e2-867c-bdf843899627_NewJeans%20-%20ETA.mp3";
        model.addAttribute("audioFileName", audioFileName);
        return "/myPage/musicTest";
    }


    @GetMapping("/bidding-history")
    public String getBiddingHistory() {

        return "";
    }

}