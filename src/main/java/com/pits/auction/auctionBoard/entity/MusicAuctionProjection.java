package com.pits.auction.auctionBoard.entity;

import com.pits.auction.auth.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicAuctionProjection {
    private Long id;
    private String title;
    private Long maxPrice;
    private LocalDateTime endTime;
    private String albumImage;
    private String albumMusic;
    private Member authorNickname;
    private String nickname;

    public MusicAuctionProjection(Long id, String title, Long maxPrice, LocalDateTime endTime, String albumImage, String albumMusic, Member authorNickname) {
        this.id = id;
        this.title = title;
        this.maxPrice = maxPrice;
        this.endTime = endTime;
        this.albumImage = albumImage;
        this.albumMusic = albumMusic;
        this.authorNickname = authorNickname;
        if (authorNickname != null) {
            this.nickname = authorNickname.getNickname();
        }
    }
}

