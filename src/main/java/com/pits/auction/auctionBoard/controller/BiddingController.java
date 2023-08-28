package com.pits.auction.auctionBoard.controller;


import com.pits.auction.auctionBoard.dto.BiddingDTO;
import com.pits.auction.auctionBoard.dto.MusicAuctionDTO;
import com.pits.auction.auctionBoard.entity.Bidding;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.service.BiddingService;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bid")
@Slf4j
public class BiddingController {

    private final BiddingService biddingService;
    private final MusicAuctionService musicAuctionService;



    @GetMapping("/bidlist")
    public String biddingList(Model model){

        model.addAttribute("biddingList", biddingService.getAuctionBiddings());

        return "/myPage/bid/bidList";
    }

    @GetMapping("/biddetail")
    public String biddingListAuctionId(Model model, @RequestParam Long auctionId){
        MusicAuctionDTO musicAuction = musicAuctionService.getMusicAuctionById(auctionId);

        log.info(musicAuction.getContent());
        log.info(musicAuction.getAlbumImage());
        log.info(musicAuction.getAuthorNickname());
        // model.addAttribute("biddingList", biddingService.getAuctionBiddingsById(auctionId));

        return "/myPage/bid/bidList";
    }


    /* 입찰 생성 폼 (상세페이지에 기능이 들어갈 예정으로 이후 삭제) */
    @GetMapping("/create")
    public String biddingCreateForm(Model model){


        return "/myPage/bid/bidCreate";
    }

    /* 입찰 생성 기능 (상세페이지가 구현되면 이 기능 사용) */
    @PostMapping("/create")
    public String biddingCreate(@ModelAttribute BiddingDTO biddingDTO){
        Bidding test = biddingService.convertToEntity(biddingDTO);

        if (test != null) {
            biddingService.biddingWrite(test);
        } else {
            // 객체 생성 실패에 대한 처리 로직
        }

        return "/myPage/bid/bidCreate";
    }

    @GetMapping("/test")
    public String biddingMaxByAuction(){

        Long num = 2L;

        System.out.println(biddingService.getMaxBidPriceForAuction(num));

        return "/myPage/bid/bidList";
    }



    @GetMapping("/clocktest")
    public String showClock(Model model) {
        // 특정 시간을 설정 (예: 2023년 8월 31일 23시 59분 59초)
        int specificYear = 2023;
        int specificMonth = 7; // 0부터 시작
        int specificDay = 31;
        int specificHour = 23;
        int specificMinute = 59;
        int specificSecond = 59;

        long currentTimeMillis = System.currentTimeMillis();
        long specificTimeMillis = new Date(specificYear - 1900, specificMonth, specificDay, specificHour, specificMinute, specificSecond).getTime();
        long timeDifferenceSeconds = (specificTimeMillis - currentTimeMillis) / 1000; // 밀리초를 초로 변환

        model.addAttribute("timeDifference", timeDifferenceSeconds);
        return "/myPage/clockTest";
    }



}
