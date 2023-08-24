package com.pits.auction.auctionBoard.repository;

import com.pits.auction.auctionBoard.entity.Bidding;
import com.pits.auction.auctionBoard.entity.MusicGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiddingRepository extends JpaRepository<Bidding, Long> {
}
