package com.pits.auction.auctionBoard.controller;

import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.service.AdminMusicAuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminMusicAuctionController {
    private final AdminMusicAuctionService adminMusicService;
    @GetMapping("/musicAuction/list")
    public String musicAuctionList(Model model) throws Exception {
        List<MusicAuction> musicAuctionList = this.adminMusicService.getMusicAuctionList();
        model.addAttribute("musicAuctionList",musicAuctionList);
        return "/admin/plAdminMusicAuctionList";
    }
}
