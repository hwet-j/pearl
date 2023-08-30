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
/*
import com.pits.auction.auctionBoard.entity.BiddingPeriod;
import com.pits.auction.auctionBoard.entity.MusicGenre;
import com.pits.auction.auth.entity.Member;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MusicAuctionDTO {
    private Long genre;  // 장르의 ID. 프론트에서 genre 값을 선택할 때 해당 장르의 ID 값을 함께 전달합니다.
    private String title;
    private MultipartFile albumImage;  // 이미지 파일을 MultipartFile로 받습니다.
    private MultipartFile albumMusic;  // 음악 파일을 MultipartFile로 받습니다.
    private String content;
    private String authorNickname;  // 사용자의 닉네임. 사용자 인증이 있을 경우, 세션에서 이 값을 가져올 수도 있습니다.
    private Long startingBid;
    private Long biddingPeriod;
*/
}
