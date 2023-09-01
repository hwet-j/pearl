package com.pits.auction.auctionBoard.controller;

import com.pits.auction.auctionBoard.dto.MusicAuctionDTO2;
import com.pits.auction.auctionBoard.entity.BiddingPeriod;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.entity.MusicGenre;
import com.pits.auction.auctionBoard.service.BiddingPeriodService;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import com.pits.auction.auctionBoard.service.MusicGenreService;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.File;
import java.io.IOException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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

        model.addAttribute("musicAuctionDTO", new MusicAuctionDTO2());

        Member anyMember = memberService.findAnyMember();
        model.addAttribute("AnyMember", anyMember.getNickname());

        return "auction/write";
    }

    @PostMapping("/write")
    public String insertAuction(@Valid MusicAuctionDTO2 musicAuctionDTO, Errors errors,
                                HttpServletRequest request, Model model) {
        if (errors.hasErrors()) {
            for (FieldError error : errors.getFieldErrors()) {
                model.addAttribute(error.getField() + "Error", error.getDefaultMessage());
            }

            List<MusicGenre> genres = musicGenreService.findAllGenres();
            model.addAttribute("genres", genres);

            List<BiddingPeriod> biddingPeriods = biddingPeriodService.findAllPeriods();
            model.addAttribute("biddingPeriods", biddingPeriods);

            model.addAttribute("musicAuctionDTO", musicAuctionDTO); // 이미 전달된 DTO 객체를 사용합니다.

            Member anyMember = memberService.findAnyMember();
            model.addAttribute("AnyMember", anyMember.getNickname());

            return "auction/write";
        }
        //이미지 구현 여기서 하세요. 음악은 알아서ㅎㅎ.
        return "";
    }

    @RequestMapping("/read")
    public String list(Model model) {
        List<MusicAuction> musicAuctions= musicAuctionService.findAll();
        model.addAttribute("musicAuctions", musicAuctions);
        return "/auction/read";
    }

    /* 글 상세보기 (auctionId) */
    @GetMapping("/detail")
    @ResponseBody
    public Long auctionDedail(Long auctionId){
        Long id = 2L;
        Optional<MusicAuction> musicAuction = musicAuctionService.findById(id);

        // 리턴 값 남은 시간
        return  musicAuctionService.remainingTime(musicAuction.get().getEndTime());
    }


    /* 동적 시간 구현 -> 실질적인 기능은 HTML의 script에서 구현되어있고 여기서는 현재시간과 설정시간의 차이를 초단위로 변환 */
    @GetMapping("/clocktest")
    public String showClock(Model model) {

        long currentTimeMillis = System.currentTimeMillis();
        // 특정 시간을 설정
        int specificYear = 2023;
        int specificMonth = 8;
        int specificDay = 30;
        int specificHour = 12;
        int specificMinute = 0;
        int specificSecond = 0;

        LocalDateTime specificDateTime = LocalDateTime.of(specificYear, specificMonth, specificDay, specificHour, specificMinute, specificSecond);
        long specificTimeMillis = specificDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long timeDifferenceSeconds = (specificTimeMillis - currentTimeMillis) / 1000; // 밀리초를 초로 변환


        model.addAttribute("timeDifference", timeDifferenceSeconds);
        return "/myPage/clockTest";
    }

    /* 음악 목록 페이지 가져오기 */
    @GetMapping("/list")
    public String showList(Model model){
        List<MusicAuction> musicAuctions = musicAuctionService.findAll();
        model.addAttribute("musicAuctions", musicAuctions);
        return ("/auction/read");
    }

}
