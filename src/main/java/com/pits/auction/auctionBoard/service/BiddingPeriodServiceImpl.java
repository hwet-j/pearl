package com.pits.auction.auctionBoard.service;


import com.pits.auction.auctionBoard.entity.BiddingPeriod;
import com.pits.auction.auctionBoard.entity.MusicGenre;
import com.pits.auction.auctionBoard.repository.BiddingPeriodRepository;
import com.pits.auction.auctionBoard.repository.MusicGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BiddingPeriodServiceImpl implements BiddingPeriodService{
    @Autowired
    private BiddingPeriodRepository biddingPeriodRepository;

    public List<BiddingPeriod> findAllPeriods() {
        return biddingPeriodRepository.findAll();
    }

    // 필요한 다른 서비스 메서드 추가...

}
