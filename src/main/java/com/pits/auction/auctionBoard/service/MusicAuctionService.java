package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.dto.MusicAuctionDTO;
import com.pits.auction.auctionBoard.dto.MusicAuctionDTO2;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.entity.MusicAuctionProjection;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MusicAuctionService {
    MusicAuctionDTO getMusicAuctionById(Long id);

    Long saveMusicAuction(MusicAuctionDTO2 musicAuctionDTO);

    List<MusicAuction> findAll();

    Optional<MusicAuction> findById(long id);

    Long remainingTime(LocalDateTime endTime);

    MusicAuctionDTO getLastBiddingAuction(String nickname);

    Long findLastBidPriceByNickname(String nickname);

    Page<MusicAuction> getMusicByOrderByIdDesc(int page, int size);

    MusicAuctionDTO2 findDetailById(Long id);

    boolean updateStatus(Long id);

    Page<MusicAuctionProjection> findTop5ByEndTimeAfterCurrent();

    public void editMusicAuction(MusicAuctionDTO2 musicAuctionDTO2, Long id);

    MusicAuction getAuctionDetail(Long id);
    List<MusicAuction> findDetailByNickname(String authorNickname);

    public void deleteMusic(Long id);
}
