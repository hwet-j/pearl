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
public class BiddingPeriodServiceImpl implements BiddingPeriodService {
    private final BiddingPeriodRepository biddingPeriodRepository;

    //입찰 기간 목록(1week, 2week 등등) 가져오기
    @Override
    public List<BiddingPeriod> findAllPeriods() {
        return biddingPeriodRepository.findAll();
    }
}
