package com.pits.auction.auctionBoard.controller;

import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.service.AdminMusicAuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public String musicAuctionList(Model model,@PageableDefault(size = 10) Pageable pageable) throws Exception {
        Page<MusicAuction> musicAuctionList = adminMusicService.getMusicAuctionList(pageable);
        int nowPage=musicAuctionList.getPageable().getPageNumber()+1;
        int startPage=Math.max(nowPage-4,1);
        int endPage=Math.min(nowPage+4,musicAuctionList.getTotalPages());
        int firstPage=Math.max(0,0);
        int lastPage=musicAuctionList.getTotalPages()-1;
        model.addAttribute("musicAuctionList",musicAuctionList);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("firstPage",firstPage);
        model.addAttribute("lastPage",lastPage);
        return "/admin/plAdminMusicAuctionList";
    }

    //체크박스 회원삭제
    @PostMapping("/musicAuction/delete")
    public String deleteMusicAuction(@RequestParam("selectedMusicAutions")List<Long> ids) throws Exception {
        adminMusicService.deleteMusicAuction(ids);
        return "redirect:/musicAuction/list";
    }

}
