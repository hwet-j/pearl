package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.entity.MusicAuction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminMusicAuctionService {
    Page<MusicAuction> getMusicAuctionList(Pageable pageable);

    void deleteMusicAuction(List<Long> ids);



}
