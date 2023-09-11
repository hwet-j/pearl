package com.pits.auction.auctionBoard.controller;


import com.pits.auction.auctionBoard.service.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/detail")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;


    /* 찜버튼을 클릭하여 찜이 등록되고 삭제되는 기능 */
    @GetMapping("/click-wishbutton")
    public String addWishList(@RequestParam("auctionId") Long auctionId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        String message = wishListService.clickWishButton(email, auctionId);

        return "redirect:/detail?id=" + auctionId;
    }

    /* 찝버튼으로 클릭 삭제 메인페이지 버전 */
    @GetMapping("/list-click-wishbutton")
    @ResponseBody
    public ResponseEntity<Map<String, String>> listaddWishList(@RequestParam("auctionId") Long auctionId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        String message = wishListService.clickWishButton(email, auctionId);

        Map<String, String> response = new HashMap<>();
        response.put("message", message);

        return ResponseEntity.ok(response);
    }
}
