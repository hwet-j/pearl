package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.entity.AuctionComment;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.repository.AuctionCommentRepository;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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
    public void delete(AuctionComment auctionComment) {
        auctionCommentRepository.delete(auctionComment);
    }

    //댓글수정
    /* 수정을 위한 답변 상세 조회*/
       public AuctionComment getComment(Long id){ //public 접근제한자 이 메서드는 다른 클래스에서도 접근가능
        Optional<AuctionComment> auctionComment = auctionCommentRepository.findById(id);
        //entity Answer에 정의된 id 필드를 기준으로 데이터베이스에서 id 값을 가진 행(row)를 찾는다
        if(auctionComment.isPresent()){
            return auctionComment.get();
        }else{
            throw new DataNotFoundException("Comment not Found");
        }

    }

    //댓글추천



}
