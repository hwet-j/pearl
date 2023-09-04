package com.pits.auction.auctionBoard.repository;

import com.pits.auction.auctionBoard.entity.Bidding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BiddingRepository extends JpaRepository<Bidding, Long> {

    @Query("SELECT MAX(b.price) FROM Bidding b WHERE b.auctionId.id = :auctionId")
    Optional<Long> findMaxPriceByAuctionId(@Param("auctionId") Long auctionId);

    Optional<Bidding> findById(Long id);

    @Query("SELECT b.auctionId.id FROM Bidding b " +
            "WHERE b.bidder.nickname = :nickname " +
            "ORDER BY b.bidTime DESC LIMIT 1")
    Long findLastAuctionIdByNickname(@Param("nickname") String nickname);


    @Query("SELECT b.price " +
            "FROM Bidding b " +
            "WHERE b.bidder.nickname = :nickname " +
            "ORDER BY b.bidTime DESC " +
            "LIMIT 1")
    Long findLastBidPriceByNickname(@Param("nickname") String nickname);


}
