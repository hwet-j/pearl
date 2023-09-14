package com.pits.auction.auctionBoard.controller;

import com.pits.auction.auctionBoard.entity.AuctionComment;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.service.CommentService;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import com.pits.auction.auctionBoard.validation.CommentForm;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.service.MemberService;
import com.pits.auction.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;


@Controller
@RequestMapping
@RequiredArgsConstructor
public class CommentController {
    private final MemberService memberService;
    private final MusicAuctionService musicAuctionService;
    private final UserService userService;
    private final CommentService commentService;


    @GetMapping("/comment/delete/{id}")
    public String answerDelete(@PathVariable("id") Long id,
                               Principal principal){
        //1 파라미터받기
        //2 비지니스로직수행
        AuctionComment auctionComment=commentService.getComment(id);//답변상세가져오기
        if(!auctionComment.getAuthorNickname().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다");
        }
        commentService.delete(auctionComment);
        return "redirect:/detail?id="+auctionComment.getMusicAuction().getId();//질문상세조회요청

    }


    //@PreAuthorize("isAuthenticated()") //로그인인증- 09.08 12:35 추가 HTML 2번째줄 사용 안할 시 삭제
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment/add/{id}") //* musicAuction.id를 받아오는 게 맞는지??*//*
    public String addComment(@PathVariable("id") Long id, Model model,
                             @Valid CommentForm commentForm, BindingResult bindingResult,
                             Principal principal) {
        //@PathVariable-URL로부터 값을 가져오는 파라미터

        MusicAuction musicAuction=musicAuctionService.getAuctionDetail(id);
        //1-1. 댓글을 달 상세 페이지 글번호를 가져온다

        //1 파라미터 받기

        if(bindingResult.hasErrors()){
            model.addAttribute("commentForm", commentForm);
            return  String.format("redirect:/detail?id=%d", id); //문제가 존재하면
        }
        //1-2. 댓글을 작성할 상세 페이지가 없으면 페이지로 돌아가고 문구를 보낸다?

        //1-3. //현재로그인한 사용자 정보를 가져옵니다. 회원번호로 가능한지?
        Member member=memberService.getUser(principal.getName());


        //1-4. 댓글 서비스로 추가한다.
        commentService.add(musicAuction, commentForm.getComment(), member);
        //3 Model

        //4 View   /detail/57
        return String.format("redirect:/detail?id=%d", id); //주소수정해야됨

    }

}
