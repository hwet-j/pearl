package com.pits.auction.auctionBoard.service;


import com.pits.auction.auctionBoard.repository.MusicAuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicAuctionServiceImpl implements MusicAuctionService{

    private final MusicAuctionRepository musicAuctionRepository;
}
