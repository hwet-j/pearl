package com.pits.auction.auctionBoard.controller;


import com.pits.auction.auctionBoard.dto.BiddingDTO;
import com.pits.auction.auctionBoard.dto.MusicAuctionDTO;
import com.pits.auction.auctionBoard.entity.Bidding;
import com.pits.auction.auctionBoard.service.BiddingService;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bid")
public class BiddingController {

    private final BiddingService biddingService;
    private final MusicAuctionService musicAuctionService;

    /* 입찰 전체 목록 */
    @GetMapping("/bidlist")
    public String biddingList(Model model){

        model.addAttribute("biddingList", biddingService.getAuctionBiddings());

        return "/myPage/bid/bidList";
    }


    /* 특정 입찰 정보(id) */
    @GetMapping("/biddetail")
    public String biddingById(Model model, @RequestParam("bidding") Long id){
        BiddingDTO biddingDTO = biddingService.findById(id);

        model.addAttribute("bidding", biddingDTO);

        return "/myPage/bid/bidDetail";
    }

    /* 입찰 목록(auctionId) - Test */
    @GetMapping("/biddetail2")
    public String biddingListAuctionId(Model model, @RequestParam Long auctionId){
        MusicAuctionDTO musicAuction = musicAuctionService.getMusicAuctionById(auctionId);

        // model.addAttribute("bidding", biddingService.getAuctionBiddingsById(auctionId));

        return "plMain";
    }


    /* 입찰 생성 폼 (상세페이지에 기능이 들어갈 예정으로 이후 삭제) */
    @GetMapping("/create")
    public String biddingCreateForm(Model model){

        return "/myPage/bid/bidCreate";
    }

    /* 입찰 생성 기능 (상세페이지가 구현되면 이 기능 사용) */
    @PostMapping("/create")
    public String biddingCreate(@ModelAttribute BiddingDTO biddingDTO){
        Bidding test = biddingService.createBidding(biddingDTO);

        if (test != null) {
            biddingService.biddingWrite(test);
        } else {
            // 객체 생성 실패에 대한 처리 로직
        }

        return "/myPage/bid/bidCreate";
    }


    /* 특정 경매 물품에 대한 최대 입찰가 */
    @GetMapping("/test")
    public String biddingMaxByAuction(){

        Long num = 2L;

        System.out.println(biddingService.getMaxBidPriceForAuction(num));

        return "/myPage/bid/bidList";
    }


    /* 동적 시간 구현 -> 실질적인 기능은 HTML의 script에서 구현되어있고 여기서는 현재시간과 설정시간의 차이를 초단위로 변환 */
    @GetMapping("/clocktest")
    public String showClock(Model model) {
        
        long currentTimeMillis = System.currentTimeMillis();
        // 특정 시간을 설정
        int specificYear = 2023;
        int specificMonth = 8;
        int specificDay = 30;
        int specificHour = 12;
        int specificMinute = 0;
        int specificSecond = 0;

        LocalDateTime specificDateTime = LocalDateTime.of(specificYear, specificMonth, specificDay, specificHour, specificMinute, specificSecond);
        long specificTimeMillis = specificDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long timeDifferenceSeconds = (specificTimeMillis - currentTimeMillis) / 1000; // 밀리초를 초로 변환


        model.addAttribute("timeDifference", timeDifferenceSeconds);
        return "/myPage/clockTest";
    }



}
