package com.pits.auction.auctionBoard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pits.auction.auth.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
@EntityListeners(AuditingEntityListener.class)
public class MusicAuction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    @JoinColumn(name = "author_nickname",  referencedColumnName = "nickname", nullable = false)
    private Member authorNickname;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    @JoinColumn(name = "genre_id", nullable = false)
    private MusicGenre genre;

    @Column(nullable = false)
    private String title;

    @Column(name = "album_image")
    private String albumImage;

    @Column(name = "album_music")
    private String albumMusic;  //작성자가 지정한 음악 이름

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "starting_bid", nullable = false)
    private Long startingBid;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.REMOVE)
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

    @PrePersist
    public void setEndTimeUsingBiddingPeriod() {
        if (this.createdAt == null || this.biddingPeriod == null) {
            throw new IllegalStateException("Cannot set end time without created time or bidding period.");
        }

        String periodValue = this.biddingPeriod.getPeriodValue();
        Pattern pattern = Pattern.compile("(\\d+)\\s*(\\D+)");
        Matcher matcher = pattern.matcher(periodValue);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid bidding period format: " + periodValue);
        }

        int value = Integer.parseInt(matcher.group(1));
        String unit = matcher.group(2);

        switch (unit) {
            case "주":
                this.endTime = this.createdAt.plusWeeks(value);
                break;
            case "개월":
                this.endTime = this.createdAt.plusMonths(value);
                break;
            default:
                throw new IllegalArgumentException("Unknown bidding period unit: " + unit);
        }
    }


}