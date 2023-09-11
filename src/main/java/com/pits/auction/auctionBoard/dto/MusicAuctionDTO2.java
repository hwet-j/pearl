package com.pits.auction.auctionBoard.dto;

import com.pits.auction.auctionBoard.entity.BiddingPeriod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MusicAuctionDTO2 {
    private Long id;
    private Long genre;
    private String genreName;
    private String title;
    private String albumImagePath;
    private String albumMusicPath;
    private String content;
    private String authorNickname;
    @Min(value = 10000, message = "입찰 시작 최소 가격은 10000원입니다")
    private Long startingBid;
    private Long biddingPeriod;
}