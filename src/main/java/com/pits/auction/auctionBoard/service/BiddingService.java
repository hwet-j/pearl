package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.dto.BiddingDTO;
import com.pits.auction.auctionBoard.entity.Bidding;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BiddingService{

    BiddingDTO findById(Long id);

    String createBidding(BiddingDTO biddingDTO);

    List<Bidding> getAuctionBiddingsById(Long auctionId);

    List<Bidding> getAuctionBiddings();

    Long getMaxBidPriceForAuction(Long auctionId);

    Long totalPriceProcessingLastBiddingByNickname(String nickname);

    Long findMaxPriceByNicknameAndAuctionId(String nickname, Long auctionId);
}
