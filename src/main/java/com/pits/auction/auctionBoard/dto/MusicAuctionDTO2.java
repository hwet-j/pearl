package com.pits.auction.auctionBoard.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicAuctionDTO2 {
    private Long id;

    @NotNull(message = "장르를 선택해 주세요")
    private Long genre;

    private String genreName;

    @NotEmpty(message = "제목을 입력해 주세요")
    private String title;

    private String albumImagePath;
    private String albumMusicPath;
    private String content;
    private String authorNickname;

    @Min(value = 10000, message = "입찰 시작 최소 가격은 10000원입니다")
    private Long startingBid;

    @NotNull(message = "경매 기간을 선택해 주세요")
    private Long biddingPeriod;
}