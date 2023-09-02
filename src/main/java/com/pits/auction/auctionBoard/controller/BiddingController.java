package com.pits.auction.auctionBoard.controller;


import com.pits.auction.auctionBoard.dto.BiddingDTO;
import com.pits.auction.auctionBoard.dto.MusicAuctionDTO;
import com.pits.auction.auctionBoard.entity.Bidding;
import com.pits.auction.auctionBoard.service.BiddingService;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import com.pits.auction.auth.service.MemberService;
import com.pits.auction.global.exception.InsufficientBiddingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bid")
public class BiddingController {

    private final BiddingService biddingService;
    private final MusicAuctionService musicAuctionService;
    private final MemberService memberService;

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
        Long balance = memberService.getBalance(biddingDTO.getBidder());

        if (biddingDTO.getPrice() <= 0) {
            throw new InsufficientBiddingException("Invalid bidding amount: " + biddingDTO.getPrice());
        }

        if (balance < biddingDTO.getPrice()){
            throw new InsufficientBiddingException("금액이 부족합니다. 회원님의 계좌에는 " + balance
                    + "원이 입금되어있습니다.");
        }

        if (biddingService.getMaxBidPriceForAuction(biddingDTO.getAuctionId()) >= biddingDTO.getPrice()) {
            throw new InsufficientBiddingException("현재 입찰 최고가는 : " + biddingService.getMaxBidPriceForAuction(biddingDTO.getAuctionId())
                    + "원 입니다. 더 큰 금액을 입력해주세요");
        }

        Bidding bidding = biddingService.createBidding(biddingDTO);

        return "/myPage/bid/bidCreate";
    }


    /* 특정 경매 물품에 대한 최대 입찰가 -> auctionId 값을 받아와서 구현 되도록 설정 예정*/
    @GetMapping("/test")
    public String biddingMaxByAuction(){

        Long num = 2L;

        System.out.println(biddingService.getMaxBidPriceForAuction(num));

        return "/myPage/bid/bidList";
    }





}
