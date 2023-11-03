package com.pits.auction.auctionBoard.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.entity.MusicAuctionProjection;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import com.pits.auction.auctionBoard.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/main")
public class RestMusicAuctionController {

    @Autowired
    private MusicAuctionService musicAuctionService;

    @Autowired
    private WishListService wishListService;
    
    //목록페이지 컨트롤러
    @GetMapping("/list")
    public ModelAndView getMusic(ModelAndView modelAndView){
        Page<MusicAuction> musicAuctions = musicAuctionService.getMusicByOrderByIdDesc(0, 8);
        //아이디 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        //아이디에 따른 wish 개수 musicAuction마다 세서 가져오기 > 로그인안함 -1, 없음0, 존재1
        for (MusicAuction musicAuction : musicAuctions){
            Long auction_id = musicAuction.getId();
            Long wishExist;
            boolean isLoggedIn = !email.equals("anonymousUser");
            if(isLoggedIn) {
                wishExist = wishListService.countByEmailAndAuctionId(auction_id, email);
                musicAuction.setWish(wishExist > 0L ? 1 : 0);
            }else{
                musicAuction.setWish(-1);
            }
        }
        modelAndView.addObject("musicAuctions", musicAuctions);
        modelAndView.setViewName("auction/read");
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
        //아이디 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        if(musicAuctions!=null) {
            List<MusicAuction> content = musicAuctions.getContent();
            for (MusicAuction musicAuction : content){
                Long auction_id = musicAuction.getId();
                Long wishExist;
                boolean isLoggedIn = !email.equals("anonymousUser");
                if(isLoggedIn) {
                    wishExist = wishListService.countByEmailAndAuctionId(auction_id, email);
                    musicAuction.setWish(wishExist > 0L ? 1 : 0);
                }else{
                    musicAuction.setWish(-1);
                }
            }
            return ResponseEntity.ok(content);
        } else{
            System.out.println("null 나왔다");
            return null;
        }
    }

    /* 최고가격 5개 불러오는 메서드 */
    @ResponseBody
    @RequestMapping("/top5Music")
    public ResponseEntity<List<MusicAuctionProjection>> top5Music(){
        Page<MusicAuctionProjection> top5Musics = musicAuctionService.findTop5ByEndTimeAfterCurrent();
        if(top5Musics!=null){
            List<MusicAuctionProjection> top5MusicList = top5Musics.getContent();
            return ResponseEntity.ok(top5MusicList);
        }else{

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }



}
