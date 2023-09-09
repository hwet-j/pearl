package com.pits.auction.auctionBoard.controller;

import com.pits.auction.auctionBoard.dto.MusicAuctionDTO2;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @GetMapping()
    public List<MusicAuction> entries(@RequestParam String authorNickname, Model model){
    List<MusicAuction> entries=musicAuctionService.findDetailByNickname(authorNickname);
    return entries;
}
}
