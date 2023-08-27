package com.pits.auction.auctionBoard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pits.auction.auth.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


/*  경매정보 (경매글)
테이블 : music_auction

컬럼 :
    id : 경매 번호/ 글 번호 (Primary Key, Auto Increment)
    authorNickname : 작성자닉네임 (Not Null)
    genre : 음악장르 (FK, Not Null)
    title : 제목 (Not Null)
    albumImage : 앨범 이미지
    albumMusic : 앨범 음악
    content : 내용
    startingBid : 입찰시작가격 (Not Null)
    createdAt : 경매시작시간(글작성시간) (Not Null)
    biddingPeriod : 입찰기간 (FK, Not Null)
    endTime : 입찰종료시점 (Not Null)
    status : 상태 (진행, 종료)
*/

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "music_auction")
public class MusicAuction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "author_nickname",  referencedColumnName = "nickname", nullable = false)
    private Member authorNickname;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "genre_id", nullable = false)
    private MusicGenre genre;

    @Column(nullable = false)
    private String title;

    @Column(name = "album_image")
    private String albumImage;

    @Column(name = "album_music")
    private String albumMusic;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "starting_bid", nullable = false)
    private Long startingBid;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(nullable = false)
    private BiddingPeriod biddingPeriod;

    @Column(name = "end_time" , nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private String status;

    // 입찰 정보
    @JsonIgnore
    @OneToMany(mappedBy = "auctionId")
    private List<Bidding> auctionBiddings;


}