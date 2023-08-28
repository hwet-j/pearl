package com.pits.auction.auctionBoard.service;


import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.repository.MusicAuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicAuctionServiceImpl implements MusicAuctionService{

    private final MusicAuctionRepository musicAuctionRepository;
    @Override
    public List<MusicAuction> findAll() {
        return musicAuctionRepository.findAll();
    }

    @Override
    public Optional<MusicAuction> findById(long id) {
        return musicAuctionRepository.findById(id);
    }


}
