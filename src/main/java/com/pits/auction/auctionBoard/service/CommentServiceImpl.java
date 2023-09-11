package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.entity.AuctionComment;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.repository.AuctionCommentRepository;
import com.pits.auction.auth.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements  CommentService {
    private final AuctionCommentRepository auctionCommentRepository;

    //댓글등록
    public void add(MusicAuction musicAuction, String comment , Member member) {
    AuctionComment auctionComment = new AuctionComment();
    auctionComment.setContent(comment);
    auctionComment.setCreatedAt(LocalDateTime.now());
    auctionComment.setMusicAuction(musicAuction);
    auctionComment.setAuthorNickname(member);
    auctionCommentRepository.save(auctionComment);
    }


    //댓글삭제

    //댓글수정

    //댓글추천



}
