package com.pits.auction.auctionBoard.controller;

import com.pits.auction.auctionBoard.dto.MusicAuctionDTO2;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class AuctionDetailController {
    private final MusicAuctionService musicAuctionService;

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        System.out.println("id = " + id);
        MusicAuctionDTO2 auctionDetail = musicAuctionService.findDetailById(id);
        model.addAttribute("auctionDetail", auctionDetail);

        return "auction/detail";
    }
}
