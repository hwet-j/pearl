package com.pits.auction.auctionBoard.repository;

import com.pits.auction.auctionBoard.entity.Bidding;
import com.pits.auction.auctionBoard.entity.MusicGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BiddingRepository extends JpaRepository<Bidding, Long> {

    @Query("SELECT MAX(b.price) FROM Bidding b WHERE b.auctionId.id = :auctionId")
    Optional<Long> findMaxPriceByAuctionId(Long auctionId);

    Optional<Bidding> findById(Long id);
}
