package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.dto.BiddingDTO;
import com.pits.auction.auctionBoard.entity.Bidding;

import java.util.List;

public interface BiddingService{

    boolean biddingWrite(Bidding bidding);

    Bidding convertToEntity(BiddingDTO dto);

    List<Bidding> getAuctionBiddingsById(Long auctionId);

    List<Bidding> getAuctionBiddings();


    Long getMaxBidPriceForAuction(Long auctionId);
}
