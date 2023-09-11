package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.entity.AuctionComment;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auth.entity.Member;

import java.security.Principal;


public interface CommentService {

    void add(MusicAuction musicAuction, String comment, Member member);


}
