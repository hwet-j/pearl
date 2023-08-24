package com.pits.auction.auctionBoard.repository;

import com.pits.auction.auctionBoard.entity.MusicAuction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicAuctionRepository extends JpaRepository<MusicAuction, Long> {
}
