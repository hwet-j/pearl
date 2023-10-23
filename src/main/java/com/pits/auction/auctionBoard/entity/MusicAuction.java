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

import static java.time.LocalTime.now;


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

    @ManyToOne
    @JoinColumn(name = "author_nickname",  referencedColumnName = "nickname", nullable = false)
    private Member authorNickname;

    @ManyToOne
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

    @ManyToOne
    @JsonIgnore
    @JoinColumn(nullable = false)
    private BiddingPeriod biddingPeriod;

    @Column(name = "end_time" , nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "musicAuction", cascade = CascadeType.REMOVE)
    private List<AuctionComment> commentList;

    // 입찰 정보
    @JsonIgnore
    @OneToMany(mappedBy = "auctionId", cascade = CascadeType.REMOVE)
    private List<Bidding> auctionBiddings;


    //createdAt생성일에 따라서 endTime입찰 종료 일자를 계산하고 데이터베이스에 추가하는 콜백 메서드
    @PrePersist
    public void setEndTimeUsingBiddingPeriod() {
        if (this.createdAt == null || this.biddingPeriod == null) {
            throw new IllegalStateException("Cannot set end time without created time or bidding period.");
        }

        //Matcher객체가 문자열 형식의 입찰 기간을 패턴 정규식과 대조해 정규식과 일치하는 부분을 찾음
        String periodValue = this.biddingPeriod.getPeriodValue();
        Pattern pattern = Pattern.compile("(\\d+)\\s*(\\D+)");
        Matcher matcher = pattern.matcher(periodValue);

        //group(1) = (\\d+) 연속된 숫자, group(2) = (\\D+) 연속된 문자, \\s* = 0개 이상의 공백
        if (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "week":
                    this.endTime = this.createdAt.plusWeeks(value);
                    break;
                case "month":
                    this.endTime = this.createdAt.plusMonths(value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown bidding period unit: " + unit);
            }
        } else {
            throw new IllegalArgumentException("Invalid bidding period format: " + periodValue);
        }
    }


   @PreUpdate
    public void updateEndTimeUsingBiddingPeriod() {
        if (biddingPeriod == null) {
            throw new IllegalStateException("Cannot set end time without created time or bidding period.");
        }
        String periodValue = this.biddingPeriod.getPeriodValue();
        Pattern pattern = Pattern.compile("(\\d+)\\s*(\\D+)");
        Matcher matcher = pattern.matcher(periodValue);
       System.out.println("periodValue="+periodValue);
       System.out.println("pattern="+pattern);
       System.out.println("matcher"+matcher);

        if(this.biddingPeriod != null){

        if (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "week":
                    this.endTime = LocalDateTime.now().plusWeeks(value);
                    break;
                case "month":
                    this.endTime =  LocalDateTime.now().plusMonths(value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown bidding period unit: " + unit);
            }
        } else {
            throw new IllegalArgumentException("Invalid bidding period format: " + periodValue);
        }
    }
    }

    /* 로그인 한 유저의 wish가 맞는지 확인하는 임시용 필드 > DB 적용 안되는 필드임 */
    @Transient
    private int wish;
}