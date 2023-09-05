package com.pits.auction.auctionBoard.controller;


import com.pits.auction.auctionBoard.dto.BiddingDTO;
import com.pits.auction.auctionBoard.dto.MusicAuctionDTO;
import com.pits.auction.auctionBoard.entity.Bidding;
import com.pits.auction.auctionBoard.service.BiddingService;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import com.pits.auction.auth.service.MemberService;
import com.pits.auction.global.exception.InsufficientBiddingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @ResponseBody
    public String biddingCreate(@RequestParam("auctionId") Long auctionId,
                                @RequestParam("bidder") String bidder,
                                @RequestParam("startPrice") Long startPrice,
                                @RequestParam("bidding_price") Long biddingPrice){


        Long balance = memberService.getBalance(bidder);
        
        // 음수 불가
        if (biddingPrice <= 0) {
            return "음수는 설정 불가능합니다.";
            // throw new InsufficientBiddingException("Invalid bidding amount: " + biddingPrice);
        }
        
        // 보유한 금액보다 적어야함
        if (balance < biddingPrice){
            return "금액이 부족합니다.\n회원님의 계좌에는 " + balance + "원이 입금되어있습니다.";
            // throw new InsufficientBiddingException("금액이 부족합니다. 회원님의 계좌에는 " + balance + "원이 입금되어있습니다.");
        }
        
        // 설정된 시작가 보다 높아야함
        if (startPrice > biddingPrice){
            return "입찰 시작가는 " + startPrice + "원 입니다.\n입찰가를 확인해주세요.";
        }
        
        // 현재 최고가 보다 높아야하며, 최소 입찰 간격은 10,000원
        if (biddingService.getMaxBidPriceForAuction(auctionId) >= biddingPrice + 10000) {
            return "현재 입찰 최고가는 " + biddingService.getMaxBidPriceForAuction(auctionId)
                    + "원 이며, 최소 입찰 간격은 10,000원 입니다.\n더 큰 금액을 입력해주세요";
        }


        BiddingDTO biddingDTO = new BiddingDTO();
        biddingDTO.setAuctionId(auctionId);
        biddingDTO.setBidder(bidder);
        biddingDTO.setPrice(biddingPrice);

        // 생성하는 과정에서 발생하는 문제도 String으로 반환
        return biddingService.createBidding(biddingDTO);
    }


    @PostMapping("/submitBid")
    public ResponseEntity<String> submitBid(@RequestParam Long bidding_price) {
        if (bidding_price < 0) {
            return ResponseEntity.ok("Price Minus");
        } else {
            return ResponseEntity.ok("Success");
        }
    }


    /* 특정 경매 물품에 대한 최대 입찰가 -> auctionId 값을 받아와서 구현 되도록 설정 예정*/
    @GetMapping("/test")
    public String biddingMaxByAuction(){

        Long num = 2L;

        System.out.println(biddingService.getMaxBidPriceForAuction(num));

        return "/myPage/bid/bidList";
    }





}
