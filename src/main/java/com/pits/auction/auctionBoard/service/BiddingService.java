package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.dto.BiddingDTO;
import com.pits.auction.auctionBoard.entity.Bidding;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BiddingService{

    boolean biddingWrite(Bidding bidding);

    BiddingDTO findById(Long id);

    Bidding createBidding(BiddingDTO biddingDTO);

    List<Bidding> getAuctionBiddingsById(Long auctionId);

    List<Bidding> getAuctionBiddings();

    Long getMaxBidPriceForAuction(Long auctionId);

}
