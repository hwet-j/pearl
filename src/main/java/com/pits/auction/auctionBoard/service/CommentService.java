package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.entity.AuctionComment;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auth.entity.Member;


public interface CommentService {

    void add(MusicAuction musicAuction, String comment, Member member);

    AuctionComment getComment(Long id);

    void delete(AuctionComment auctionComment);
}
