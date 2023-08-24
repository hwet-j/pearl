package com.pits.auction.auctionBoard.repository;

import com.pits.auction.auctionBoard.entity.Bidding;
import com.pits.auction.auctionBoard.entity.BiddingPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiddingPeriodRepository extends JpaRepository<BiddingPeriod, Long> {
}
