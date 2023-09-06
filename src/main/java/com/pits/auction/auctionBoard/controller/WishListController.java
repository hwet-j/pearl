package com.pits.auction.auctionBoard.controller;


import com.pits.auction.auctionBoard.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;


    @GetMapping("/add-wishlist")
    public String addWishList() {

        String memberNickname = "qwerty";
        Long auctionId = 2L;

        wishListService.addWishList(memberNickname, auctionId);
        return "myPage/userRead"; // 이동할 URL을 지정해주세요.
    }
}
