package com.pits.auction.auctionBoard.controller;

import com.pits.auction.auctionBoard.dto.MusicAuctionDTO;
import com.pits.auction.auctionBoard.dto.MusicAuctionDTO2;
import com.pits.auction.auctionBoard.entity.AuctionComment;
import com.pits.auction.auctionBoard.entity.BiddingPeriod;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.entity.MusicGenre;
import com.pits.auction.auctionBoard.repository.MusicAuctionRepository;
import com.pits.auction.auctionBoard.service.*;
import com.pits.auction.auctionBoard.validation.CommentForm;
import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.repository.MemberRepository;
import com.pits.auction.global.upload.AudioUpload;
import com.pits.auction.global.upload.ImageUpload;
import com.pits.auction.user.service.UserSecurityService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MusicAuctionController {
    private final MusicAuctionService musicAuctionService;
    private final MusicAuctionRepository musicAuctionRepository;
    private final MusicGenreService musicGenreService;
    private final BiddingPeriodService biddingPeriodService;
    private final BiddingService biddingService;
    private final WishListService wishListService;
    private final UserSecurityService userSecurityService;
    private final MemberRepository memberRepository;
    private final ImageUpload imageUpload;
    private final AudioUpload audioUpload;

    //작성페이지 컨트롤러
    @GetMapping("/write")
    public String writePage(Model model) {
        //장르 목록(dance, jazz...)을 모두 찾아서 뷰단에서 보여주기
        List<MusicGenre> genres = musicGenreService.findAllGenres();
        model.addAttribute("genres", genres);

        //입찰 기간(1week, 2month...)을 모두 찾아서 뷰단에서 보여주기
        List<BiddingPeriod> biddingPeriods = biddingPeriodService.findAllPeriods();
        model.addAttribute("biddingPeriods", biddingPeriods);

        //뷰단에서 보여지는 입력 요소들과 타임리프에서 바인딩
        model.addAttribute("musicAuctionDTO", new MusicAuctionDTO2());
        return "auction/write";
    }

    //작성페이지 컨트롤러
    @PostMapping("/write")
    public String insertAuction(@Valid MusicAuctionDTO2 musicAuctionDTO, BindingResult bindingResult, Model model,
                                @RequestParam("albumImage") MultipartFile image,
                                @RequestParam("albumMusic") MultipartFile music) {
        //이메일로 로그인한 사용자의 정보 찾기
        String currentUserEmail = userSecurityService.getCurrentUserEmail();
        Member currentUser = memberRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        //로그인한 사용자의 닉네임을 DTO의 AuthorNickname으로 설정
        musicAuctionDTO.setAuthorNickname(currentUser.getNickname());

        //DTO의 유효성 검사시 오류가 있다면 뷰단에서 오류가 난 필드에 내가 지정한 에러 메시지가 보임
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                model.addAttribute(error.getField() + "Error", error.getDefaultMessage());
                System.out.println("Field: " + error.getField() + ", Message: " + error.getDefaultMessage());
            }

            List<MusicGenre> genres = musicGenreService.findAllGenres();
            model.addAttribute("genres", genres);

            List<BiddingPeriod> biddingPeriods = biddingPeriodService.findAllPeriods();
            model.addAttribute("biddingPeriods", biddingPeriods);

            //사용자가 제출한 폼을 에러 메시지와 함께 다시 보여주기
            model.addAttribute("musicAuctionDTO", musicAuctionDTO);
            return "auction/write";
        }

        //MusicAuction 엔티티(작성페이지 엔티티)에서 최고 높은 ID 찾기
        Long maxId = musicAuctionRepository.findMaxId();
        if (maxId == null) {
            maxId = 0L; // 기본값 설정
        }

        //이미지 업로드 후 dto에 이미지 경로 설정, 업로드 하지 않았다면 오류 메시지 띄우기
        if (!image.isEmpty()){
            musicAuctionDTO.setAlbumImagePath(imageUpload.uploadImage(image,musicAuctionRepository.findMaxId()+1 , "auction"));
        } else {
            model.addAttribute("imageError", "이미지를 반드시 업로드해야 합니다");
            return "auction/write";
        }

        //음악 업로드 후 dto에 오디오 경로 설정, 업로드 하지 않았다면 오류 메시지 띄우기
        if (!music.isEmpty()){
            musicAuctionDTO.setAlbumMusicPath(audioUpload.uploadAudio(music, musicAuctionRepository.findMaxId()+1));
        } else {
            model.addAttribute("musicError", "음악 파일을 반드시 업로드해야 합니다");
            return "auction/write";
        }

        //사용자가 제출한 폼을 dto와 바인딩하고 서비스 레이어를 통해 데이터베이스에 저장
        Long savedAuctionId = musicAuctionService.saveMusicAuction(musicAuctionDTO);
        //상세 페이지로 redirect
        return "redirect:/detail?id=" + savedAuctionId;
    }

    @RequestMapping("/read")
    public String list(Model model) {
        List<MusicAuction> musicAuctions= musicAuctionService.findAll();
        model.addAttribute("musicAuctions", musicAuctions);
        return "auction/read";
    }

    /* 글 상세보기 (auctionId) */
    @GetMapping("/detail")
    public String auctionDedail(@RequestParam("id") Long auctionId, Model model,CommentForm commentForm, AuctionComment auctionComment){
        Long id = 42L;
        // 경매글 가져오기
        Optional<MusicAuction> optionalMusicAuction = musicAuctionService.findById(auctionId);
        if (optionalMusicAuction.isPresent()){
            MusicAuction musicAuction = optionalMusicAuction.get();

            if(musicAuction.getStatus().equals("진행")){      // 경매입찰 관련 정보는 경매가 진행되는 동안만 필요함
                // 경매 남은 시간 계산
                Long remainingTime = musicAuctionService.remainingTime(musicAuction.getEndTime());

                if (remainingTime == null){     // remainingTime 이 음수면 null값을 반환함
                    musicAuctionService.updateStatus(musicAuction.getId());     // 상태 "종료"로 변경
                }

                model.addAttribute("remainingTime", remainingTime);
            }

            // 입찰 기록이 존재하면
            if (biddingService.getAuctionBiddingsById(musicAuction.getId()) != null){
                // 경매에 대한 입찰 기록이 있을 때 최대 경매가 반환
                model.addAttribute("maxBiddingPrice", biddingService.getMaxBidPriceForAuction(musicAuction.getId()));
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) {
                String email = authentication.getName();
                Optional<Member> optionalMember = memberRepository.findByEmail(email);

                // 사용자 정보가 존재하는 경우에만 처리
                if (optionalMember.isPresent()) {
                    model.addAttribute("wish", wishListService.countByMemberNicknameAndAuctionId(optionalMember.get(), musicAuction));
                }
            }
            model.addAttribute("entries",musicAuctionService.findDetailByNickname(musicAuction.getAuthorNickname().getNickname()));
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
        MusicAuctionDTO2 existingAuction = musicAuctionService.findDetailById(id);

        if (!albumImage.isEmpty()){   // 파일이 있을 경우에만 파일 업로드 진행
            // 이미지 저장과 경로 DTO에 저장
            musicAuctionDTO2.setAlbumImagePath(imageUpload.uploadImage(albumImage, id, "auction"));
        }else {
            // 이미지 파일이 제출되지 않은 경우, 이전 이미지 경로를 그대로 사용
            musicAuctionDTO2.setAlbumImagePath(existingAuction.getAlbumImagePath());
        }
        if (!albumMusic.isEmpty()){   // 파일이 있을 경우에만 파일 업로드 진행
            // 이미지 저장과 경로 DTO에 저장
            musicAuctionDTO2.setAlbumMusicPath(audioUpload.uploadAudio(albumMusic, id));
        }else {
            // 오디오 파일이 제출되지 않은 경우, 이전 오디오 경로를 그대로 사용
            musicAuctionDTO2.setAlbumMusicPath(existingAuction.getAlbumMusicPath());
        }
        System.out.println("DTO2="+musicAuctionDTO2.getBiddingPeriod());
        System.out.println("DTO2="+musicAuctionDTO2.getBiddingPeriod());
        System.out.println("DTO2="+musicAuctionDTO2.getBiddingPeriod());
        System.out.println("DTO2="+musicAuctionDTO2.getBiddingPeriod());
        musicAuctionService.editMusicAuction(musicAuctionDTO2,id);

        return String.format("redirect:/detail?id=%d",id);
    }



}