package com.pits.auction.auctionBoard.controller;


import com.pits.auction.auctionBoard.dto.BiddingDTO;
import com.pits.auction.auctionBoard.entity.Bidding;
import com.pits.auction.auctionBoard.service.BiddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bid")
public class BiddingController {

    private final BiddingService biddingService;


    @GetMapping("/bidlist")
    public String biddingList(Model model){

        model.addAttribute("biddingList", biddingService.getAuctionBiddings());

        return "/myPage/bid/bidList";
    }

    @GetMapping("/biddetail")
    public String biddingListAuctionId(Model model, @RequestParam Long auctionId){

        model.addAttribute("biddingList", biddingService.getAuctionBiddingsById(auctionId));

        return "/myPage/bid/bidList";
    }


    /* 입찰 생성 폼 (상세페이지에 기능이 들어갈 예정으로 이후 삭제) */
    @GetMapping("/create")
    public String biddingCreateForm(){
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





}
