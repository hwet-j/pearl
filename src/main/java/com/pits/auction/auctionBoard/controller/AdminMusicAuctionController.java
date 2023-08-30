package com.pits.auction.auctionBoard.controller;

import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.service.AdminMusicAuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    //체크박스 회원삭제
    @PostMapping("/musicAuction/delete")
    public String deleteMusicAuction(@RequestParam("selectedMusicAutions")List<Long> ids) throws Exception {
        adminMusicService.deleteMusicAuction(ids);
        return "redirect:/musicAuction/list";
    }

}
