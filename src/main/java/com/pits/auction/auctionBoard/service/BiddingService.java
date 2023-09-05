package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.dto.BiddingDTO;
import com.pits.auction.auctionBoard.entity.Bidding;

import java.util.List;

public interface BiddingService{

    BiddingDTO findById(Long id);

    String createBidding(BiddingDTO biddingDTO);

    List<Bidding> getAuctionBiddingsById(Long auctionId);

    List<Bidding> getAuctionBiddings();

    Long getMaxBidPriceForAuction(Long auctionId);

}
