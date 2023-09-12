package com.pits.auction.auctionBoard.repository;

import com.pits.auction.auctionBoard.entity.Bidding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
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


    @Query("SELECT MAX(b.price) " +
            "FROM Bidding b " +
            "WHERE b.bidder.nickname = :nickname " +
            "AND b.status = '진행' " +
            "GROUP BY b.auctionId.id")
    List<Long> findProcessingMaxPriceBiddingByNickname(@Param("nickname")String nickname);

    @Query("SELECT MAX(b.price) FROM Bidding b WHERE 1=1 " +
            "AND b.bidder.nickname = :nickname " +
            "AND b.auctionId.id = :auctionId")
    Long findMaxPriceByNicknameAndAuctionId(@Param("nickname") String nickname, @Param("auctionId") Long auctionId);

    @Modifying
    @Query("UPDATE Bidding b " +
            "SET b.status = '타인 입찰상회' " +
            "WHERE " +
            "b.auctionId.id = :auctionId " +
            "AND b.price < :price")
    void updateMaxPriceBiddingStatus(@Param("auctionId") Long auctionId, @Param("price") Long price);


}
