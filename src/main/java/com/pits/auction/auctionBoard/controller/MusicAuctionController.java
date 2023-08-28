package com.pits.auction.auctionBoard.controller;


import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import com.pits.auction.auctionBoard.service.MusicAuctionServiceImpl;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MusicAuctionController {

    private final MusicAuctionService musicAuctionService;
    @RequestMapping("/read")
    public String list(Model model) {
        List<MusicAuction> musicAuctions= musicAuctionService.findAll();
        model.addAttribute("musicAuctions", musicAuctions);
        return "/auction/read";
    }


}
