package com.pits.auction.auctionBoard.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.pits.auction.auctionBoard.entity.MusicAuction;
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
        Page<MusicAuction> musicAuctions = musicAuctionService.getMusicByOrderByIdDesc(page, 16);
        modelAndView.addObject("musicAuctions", musicAuctions);
        modelAndView.setViewName("/auction/read");
        List<MusicAuction> musicAuctionList = musicAuctionService.findAllByOrderByEndTime();

        List<Long> remainingTimes = new ArrayList<>();
        for(MusicAuction musicAuction : musicAuctionList){
            Long remainingTime = musicAuctionService.remainingTime(musicAuction.getEndTime());
            remainingTimes.add(remainingTime);
        }
        modelAndView.addObject("musicAuctionList",musicAuctionList);
        modelAndView.addObject("remainingTimes", remainingTimes);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/getMore/{page}")
    public ResponseEntity<List<MusicAuction>> moreMusic(@PathVariable("page") int page) {
        Page<MusicAuction> musicAuctions = musicAuctionService.getMusicByOrderByIdDesc(page+4, 4);
        List<MusicAuction> content = musicAuctions.getContent();
        for (MusicAuction auction : content) {
            auction.setAlbumImage("/images/" + auction.getAlbumImage());
        }
        return ResponseEntity.ok(content);
    }
}
