package com.pits.auction.auth.controller;

import com.pits.auction.auctionBoard.service.BiddingService;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.service.MemberService;
import com.pits.auction.global.exception.InsufficientBalanceException;
import com.pits.auction.global.upload.AudioUpload;
import com.pits.auction.global.upload.ImageUpload;
import jakarta.transaction.Transactional;
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


    /* 유저 전체 리스트 - 마이페이지에서는 필요없으나 테스트를 위해 작성 */
    @GetMapping("/userlist")
    public String getUserList(Model model) {
        
        model.addAttribute("userInfoList", memberService.getUserList());
        
        return "/myPage/plMyPage";
    }


    /* 특정 유저 정보 (id) */
    @GetMapping("/userinfo")
    public String getUserInfo(@RequestParam("userId") Long userId, Model model) {

        model.addAttribute("userInfo", memberService.getUserInfo(userId));

        return "/myPage/userRead";
    }


    /* 특정 유저 수정 폼 */
    @GetMapping("/useredit")
    public String formUserEdit(@RequestParam("userId") Long userId, Model model) {
        MemberDTO userInfo = memberService.getUserInfo(userId);
        model.addAttribute("userInfo", userInfo);
        return "/myPage/userEdit";
    }


    /* 특정 유저 수정 작업 */
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

        memberService.updateUserInfo(userInfo);

        return "redirect:/mypage/userlist";
    }


    /* 회원 삭제 요청 */
    @GetMapping("/userdelete")
    public String requestUserDelete(@RequestParam Long userId) {

        MemberDTO memberDTO = memberService.getUserInfo(userId);

        memberService.requestUserDelete(memberDTO);
        return "redirect:/mypage/userlist";
    }

    /* 음악 재생 테스트를 위해 작성 -> 다른곳에서 기능 구현후 삭제 */
    @GetMapping("/musictest")
    public String musicTest(Model model) {
        String audioFileName = "d6287fd2-14f9-4607-9f86-b84277771fe1_NewJeans - ETA.mp3";
        model.addAttribute("audioFileName", audioFileName);
        return "/myPage/musicTest";
    }

    /* 입찰 내역 */
    @GetMapping("/bidding-history")
    public String getBiddingHistory() {

        return "";
    }


    @GetMapping("/balance")
    public String getBalance(@RequestParam Long userId, Model model) {

        model.addAttribute("userInfo", memberService.getUserInfo(userId));

        return "/myPage/userBalance";
    }


    @PostMapping("/balance")
    public String transactionBalance(
            @RequestParam Long balance,
            @RequestParam Long userId,
            @RequestParam String action,
            Model model) {

        try {
            if ("deposit".equals(action)) {
                memberService.addBalance(userId, balance);
            } else if ("withdraw".equals(action)) {
                memberService.minusBalance(userId, balance);
            }
        } catch (InsufficientBalanceException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error/insufficientBalance"; // 예외 페이지로 이동
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error/invalidAmount";       // 다른 예외 페이지로 이동
        }

        return "redirect:/mypage/userinfo?userId=" + userId;
    }


}