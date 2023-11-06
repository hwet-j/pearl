package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.entity.BiddingPeriod;

import java.util.List;

public interface BiddingPeriodService {
    List<BiddingPeriod> findAllPeriods();
}
