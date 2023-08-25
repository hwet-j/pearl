package com.pits.auction.auctionBoard.controller;


import com.pits.auction.auctionBoard.dto.BiddingDTO;
import com.pits.auction.auctionBoard.service.BiddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String biddingCreate(@RequestParam Long price){
        System.out.println(price);


        return "/myPage/bid/bidCreate";
    }


}
