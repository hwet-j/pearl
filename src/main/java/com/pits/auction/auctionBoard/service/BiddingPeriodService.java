package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.entity.BiddingPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BiddingPeriodService {
    List<BiddingPeriod> findAllPeriods();
}
