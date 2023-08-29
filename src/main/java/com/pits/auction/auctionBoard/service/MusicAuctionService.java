package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.dto.MusicAuctionDTO;
import com.pits.auction.auctionBoard.entity.MusicAuction;

public interface MusicAuctionService {
    boolean saveMusicAuction(MusicAuctionDTO musicAuctionDTO);
}
