package com.pits.auction.auctionBoard.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MusicAuctionDTO2 {
    private Long id;
    private Long genre;  // 장르의 ID. 프론트에서 genre 값을 선택할 때 해당 장르의 ID 값을 함께 전달합니다.
    private String genreName;
    private String title;
    private String albumImagePath;
    private String albumMusicPath;
    private String content;
    private String authorNickname;  // 사용자의 닉네임. 사용자 인증이 있을 경우, 세션에서 이 값을 가져올 수도 있습니다.

    @Min(value = 10000, message = "입찰 시작 최소 가격은 10000원입니다")
    private Long startingBid;

    private Long biddingPeriod;

}
