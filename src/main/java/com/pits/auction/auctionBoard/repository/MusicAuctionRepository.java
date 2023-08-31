package com.pits.auction.auctionBoard.repository;

import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicAuctionRepository extends JpaRepository<MusicAuction, Long> {

    @Query("SELECT m FROM MusicAuction m WHERE m.status = 'Active'")
    List<MusicAuction> findAllActiveAuctions();

}
