package com.pits.auction.auctionBoard.controller;


import com.pits.auction.auctionBoard.dto.MusicAuctionDTO;
import com.pits.auction.auctionBoard.entity.BiddingPeriod;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.entity.MusicGenre;
import com.pits.auction.auctionBoard.repository.BiddingPeriodRepository;
import com.pits.auction.auctionBoard.repository.MusicGenreRepository;
import com.pits.auction.auctionBoard.service.BiddingPeriodService;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import com.pits.auction.auctionBoard.service.MusicGenreService;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;

import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import com.pits.auction.auctionBoard.service.MusicAuctionServiceImpl;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MusicAuctionController {
    private final MusicAuctionService musicAuctionService;

    private final MusicGenreService musicGenreService;
    private final BiddingPeriodService biddingPeriodService;
    private final MemberService memberService;

    @GetMapping("/write")
    public String writePage(Model model) {
        List<MusicGenre> genres = musicGenreService.findAllGenres();
        model.addAttribute("genres", genres);

        List<BiddingPeriod> biddingPeriods = biddingPeriodService.findAllPeriods();
        model.addAttribute("biddingPeriods", biddingPeriods);

        model.addAttribute("musicAuctionDTO", new MusicAuctionDTO());

        Member anyMember = memberService.findAnyMember();
        model.addAttribute("AnyMember", anyMember.getNickname());

        return "auction/write";
    }

    @PostMapping("/write")
    public String insertAuction(MusicAuctionDTO musicAuctionDTO, HttpServletRequest request) {
        try {
            // MultipartFile로 받은 파일들을 저장하기 위한 로직
            String rootPath = request.getSession().getServletContext().getRealPath("/"); // 실제 경로를 구하는 예시
            String basePath = rootPath + "uploads/";

            File albumImageFile = new File(basePath + musicAuctionDTO.getAlbumImage().getOriginalFilename());
            musicAuctionDTO.getAlbumImage().transferTo(albumImageFile);

            File albumMusicFile = new File(basePath + musicAuctionDTO.getAlbumMusic().getOriginalFilename());
            musicAuctionDTO.getAlbumMusic().transferTo(albumMusicFile);
            System.out.println(musicAuctionDTO.getBiddingPeriod());
            System.out.println(musicAuctionDTO.getBiddingPeriod());
            System.out.println(musicAuctionDTO.getBiddingPeriod());
            System.out.println(musicAuctionDTO.getBiddingPeriod());
            System.out.println(musicAuctionDTO.getBiddingPeriod());
          // 음악 경매 정보를 데이터베이스에 저장하기 위한 서비스 호출
            musicAuctionService.saveMusicAuction(musicAuctionDTO);
          return "redirect:/write"; // 성공 페이지로 리다이렉트
        } catch (IOException e) {
            e.printStackTrace();
            return "error"; // 에러 페이지로 이동
        }
    }

    @RequestMapping("/read")
    public String list(Model model) {
        List<MusicAuction> musicAuctions= musicAuctionService.findAll();
        model.addAttribute("musicAuctions", musicAuctions);
        return "/auction/read";
    }           

}
