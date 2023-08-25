package com.pits.auction.auctionBoard.controller;


import com.pits.auction.auctionBoard.service.BiddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bid")
public class BiddingController {

    private final BiddingService biddingService;

    public String biddingList(){
        return "";
    }



    @GetMapping("/create")
    public String biddingCreateForm(){
        return "/myPage/bid/bidCreate";
    }

    @PostMapping("/create")
    public String biddingCreate(){
        return "/myPage/bid/bidCreate";
    }


}
