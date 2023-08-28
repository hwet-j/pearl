package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.entity.MusicAuction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MusicAuctionService {

    List<MusicAuction> findAll();

    Optional<MusicAuction> findById(long id);
}
