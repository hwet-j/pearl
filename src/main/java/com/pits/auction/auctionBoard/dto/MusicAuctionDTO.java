package com.pits.auction.auctionBoard.dto;

import com.pits.auction.auctionBoard.entity.MusicAuction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MusicAuctionDTO {

    private Long id;
    private String authorNickname;
    private Long genreId;
    private String title;
    private String albumImage;
    private String albumMusic;
    private String content;
    private Long startingBid;
    private LocalDateTime createdAt;
    private Long biddingPeriodId;
    private LocalDateTime endTime;
    private String status;

    public static MusicAuctionDTO fromEntity(MusicAuction musicAuction) {
        return MusicAuctionDTO.builder()
                .id(musicAuction.getId())
                .authorNickname(musicAuction.getAuthorNickname().getNickname())
                .genreId(musicAuction.getGenre().getId())
                .title(musicAuction.getTitle())
                .albumImage(musicAuction.getAlbumImage())
                .albumMusic(musicAuction.getAlbumMusic())
                .content(musicAuction.getContent())
                .startingBid(musicAuction.getStartingBid())
                .createdAt(musicAuction.getCreatedAt())
                .biddingPeriodId(musicAuction.getBiddingPeriod().getId())
                .endTime(musicAuction.getEndTime())
                .status(musicAuction.getStatus())
                .build();
    }
}
