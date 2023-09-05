package com.pits.auction.auctionBoard.controller;

import com.pits.auction.auctionBoard.dto.MusicAuctionDTO;
import com.pits.auction.auctionBoard.dto.MusicAuctionDTO2;
import com.pits.auction.auctionBoard.entity.BiddingPeriod;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.entity.MusicGenre;
import com.pits.auction.auctionBoard.service.BiddingPeriodService;
import com.pits.auction.auctionBoard.service.BiddingService;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import com.pits.auction.auctionBoard.service.MusicGenreService;
import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.repository.MemberRepository;
import com.pits.auction.auth.service.MemberService;
import com.pits.auction.global.upload.AudioUpload;
import com.pits.auction.global.upload.ImageUpload;
import com.pits.auction.user.service.UserSecurityService;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final BiddingService biddingService;
    private final MemberService memberService;
    private final UserSecurityService userSecurityService;
    private final MemberRepository memberRepository;
    private final ImageUpload imageUpload;
    private final AudioUpload audioUpload;


    @GetMapping("/write")
    public String writePage(Model model) {
        List<MusicGenre> genres = musicGenreService.findAllGenres();
        model.addAttribute("genres", genres);

        List<BiddingPeriod> biddingPeriods = biddingPeriodService.findAllPeriods();
        model.addAttribute("biddingPeriods", biddingPeriods);

        model.addAttribute("musicAuctionDTO", new MusicAuctionDTO2());



        return "auction/write";
    }

    @PostMapping("/write")
    public String insertAuction(@Valid MusicAuctionDTO2 musicAuctionDTO, Errors errors,
                                HttpServletRequest request, Model model) {
        String currentUserEmail = userSecurityService.getCurrentUserEmail();
        Member currentUser = memberRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        musicAuctionDTO.setAuthorNickname(currentUser.getNickname());

//        if (currentUserNickname == null) {
//            return "redirect:/login";
//        }

        if (errors.hasErrors()) {
            for (FieldError error : errors.getFieldErrors()) {
                model.addAttribute(error.getField() + "Error", error.getDefaultMessage());
            }

            List<MusicGenre> genres = musicGenreService.findAllGenres();
            model.addAttribute("genres", genres);

            List<BiddingPeriod> biddingPeriods = biddingPeriodService.findAllPeriods();
            model.addAttribute("biddingPeriods", biddingPeriods);

            model.addAttribute("musicAuctionDTO", musicAuctionDTO); // 이미 전달된 DTO 객체를 사용합니다.

            musicAuctionService.saveMusicAuction(musicAuctionDTO);
            return "auction/write";
        }
        //이미지 구현 여기서 하세요. 음악은 알아서ㅎㅎ.
        return "redirect:/auction/detail";
    }

    @RequestMapping("/read")
    public String list(Model model) {
        List<MusicAuction> musicAuctions= musicAuctionService.findAll();
        model.addAttribute("musicAuctions", musicAuctions);
        return "/auction/read";
    }

    /* 글 상세보기 (auctionId) */
    @GetMapping("/detail")
    public String auctionDedail(Long auctionId, Model model){
        Long id = 42L;
        // 경매글 가져오기
        Optional<MusicAuction> optionalMusicAuction = musicAuctionService.findById(id);
        if (optionalMusicAuction.isPresent()){
            MusicAuction musicAuction = optionalMusicAuction.get();

            // 경매입찰 관련 정보는 경매가 진행되는 동안만 필요함
            if(musicAuction.getStatus().equals("진행")){
                // 경매 남은 시간 계산
                Long remainingTime = musicAuctionService.remainingTime(musicAuction.getEndTime());

                // remainingTime 이 음수면 null값을 반환함
                if (remainingTime == null){
                    musicAuctionService.updateStatus(musicAuction.getId());
                }

                model.addAttribute("remainingTime", remainingTime);
            }
            
            // 입찰 기록이 존재하면
            if (biddingService.getAuctionBiddingsById(musicAuction.getId()) != null){
                // 경매에 대한 입찰 기록이 있을 때 최대 경매가 반환
                model.addAttribute("maxBiddingPrice", biddingService.getMaxBidPriceForAuction(musicAuction.getId()));
            }

            model.addAttribute("genre", musicAuction.getGenre().getName());
            model.addAttribute("musicAuction", musicAuction);

            // 입찰 금액을 올리는 기능 -> % 와 같이 다른 기능을 사용할 가능성이 있어서 값 전달
            model.addAttribute("addValue1", 1000);
            model.addAttribute("addValue2", 5000);
            model.addAttribute("addValue3", 10000);

            // if (musicAuctionService.getLastBiddingAuction())

        } else {
            throw new RuntimeException("경매글 정보를 가져오지 못함");
        }

        // 경매글에 대한 최고가 가져오기

        
        // 댓글 기능 완성 후 - 해당 경매글에 대한 댓글 가져오기
        
        return  "/auction/detail";
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

    /* 음악 목록 페이지 가져오기  > 무한 스크롤 구현하여 폐기*/
 /*   @GetMapping("/list")
    public String showList(Model model){
        List<MusicAuction> musicAuctions = musicAuctionService.findAll();
        model.addAttribute("musicAuctions", musicAuctions);
        return ("/auction/read");
    }*/


    //작성 상세 페이지 수정
    @GetMapping("/edit/{id}")
    public String editMusicAuction(@PathVariable("id")Long id,Model model)throws Exception{
        MusicAuction musicAuction=musicAuctionService.getAuctionDetail(id);
        List<MusicGenre> genres = musicGenreService.findAllGenres();
        List<BiddingPeriod> biddingPeriods = biddingPeriodService.findAllPeriods();


        model.addAttribute("genres", genres);
        model.addAttribute("biddingPeriods", biddingPeriods);
        model.addAttribute("musicAuction",musicAuction);
        return "/auction/edit";
    }

    @PostMapping("/edit/{id}")
    public String modifyMusicAuction(@PathVariable("id") Long id, @ModelAttribute MusicAuctionDTO2 musicAuctionDTO2
            , @RequestParam("albumImage") MultipartFile albumImage
            ,@RequestParam("albumMusic")MultipartFile albumMusic ) throws Exception{
        if (!albumImage.isEmpty()){   // 파일이 있을 경우에만 파일 업로드 진행
            // 이미지 저장과 경로 DTO에 저장
            musicAuctionDTO2.setAlbumImagePath(imageUpload.uploadImage(albumImage));
        }
        if (!albumMusic.isEmpty()){   // 파일이 있을 경우에만 파일 업로드 진행
            // 이미지 저장과 경로 DTO에 저장
            musicAuctionDTO2.setAlbumMusicPath(audioUpload.uploadAudio(albumMusic));
        }
        musicAuctionService.editMusicAuction(musicAuctionDTO2,id);

        return String.format("redirect:/edit/%d",id);
    }

}

