package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.dto.MusicAuctionDTO;
import com.pits.auction.auctionBoard.entity.MusicAuction;

import java.util.List;
import java.util.Optional;

public interface MusicAuctionService {
    MusicAuctionDTO getMusicAuctionById(Long id);

    boolean saveMusicAuction(MusicAuctionDTO musicAuctionDTO);

    List<MusicAuction> findAll();

    Optional<MusicAuction> findById(long id);

}
