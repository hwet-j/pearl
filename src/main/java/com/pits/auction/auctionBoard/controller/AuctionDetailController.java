package com.pits.auction.auctionBoard.controller;

import com.pits.auction.auctionBoard.dto.MusicAuctionDTO2;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuctionDetailController {
    private final MusicAuctionService musicAuctionService;

    //상세페이지 컨트롤러
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        //작성페이지 완료 후 생긴 id로 상세페이지 찾기
        MusicAuctionDTO2 auctionDetail = musicAuctionService.findDetailById(id);
        model.addAttribute("auctionDetail", auctionDetail);
        //상세페이지 보여주기
        return "auction/detail";
    }

    @GetMapping()
    public List<MusicAuction> entries(@RequestParam String authorNickname, Model model){
    List<MusicAuction> entries=musicAuctionService.findDetailByNickname(authorNickname);
    return entries;
}
    @GetMapping("/detail/delete/{id}")
    public String deleteMusic(@PathVariable("id")Long id) throws Exception {
        musicAuctionService.deleteMusic(id);
        return "redirect:/main/list";
    }

}
