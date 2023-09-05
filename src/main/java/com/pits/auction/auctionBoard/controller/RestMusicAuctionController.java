package com.pits.auction.auctionBoard.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.entity.MusicAuctionProjection;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/testing")
public class RestMusicAuctionController {

    @Autowired
    private MusicAuctionService musicAuctionService;

    @GetMapping("/paging")
    public ModelAndView getMusic(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            ModelAndView modelAndView
    ){
        Page<MusicAuction> musicAuctions = musicAuctionService.getMusicByOrderByIdDesc(0, 8);
        modelAndView.addObject("musicAuctions", musicAuctions);
        modelAndView.setViewName("/auction/read");
        Page<MusicAuctionProjection> musicAuctionList = musicAuctionService.findTop5ByEndTimeAfterCurrent();

        List<Long> remainingTimes = new ArrayList<>();
        for(MusicAuctionProjection musicAuction : musicAuctionList){
            Long remainingTime = musicAuctionService.remainingTime(musicAuction.getEndTime());
            remainingTimes.add(remainingTime);
        }
        modelAndView.addObject("musicAuctionList",musicAuctionList);
        modelAndView.addObject("remainingTimes", remainingTimes);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/getMore/{page}")
    public ResponseEntity<List<MusicAuction>> moreMusic(@PathVariable("page") int page, Model model) {
        Page<MusicAuction> musicAuctions = musicAuctionService.getMusicByOrderByIdDesc(page, 4);
        if(musicAuctions!=null) {
            List<MusicAuction> content = musicAuctions.getContent();
            return ResponseEntity.ok(content);
        } else{
            System.out.println("null 나왔다");
            return null;
        }
    }
}
