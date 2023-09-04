package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.dto.MusicAuctionDTO;
import com.pits.auction.auctionBoard.dto.MusicAuctionDTO2;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.entity.MusicAuctionProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MusicAuctionService {
    MusicAuctionDTO getMusicAuctionById(Long id);

    boolean saveMusicAuction(MusicAuctionDTO2 musicAuctionDTO);

    List<MusicAuction> findAll();

    Optional<MusicAuction> findById(long id);

    Long remainingTime(LocalDateTime endTime);

    MusicAuctionDTO getLastBiddingAuction(String nickname);

    Long findLastBidPriceByNickname(String nickname);

    Page<MusicAuction> getMusicByOrderByIdDesc(int page, int size);

    MusicAuctionDTO2 findDetailById(Long id);


    public Page<MusicAuctionProjection> findTop5ByEndTimeAfterCurrent();

}
