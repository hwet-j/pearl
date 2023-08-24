package com.pits.auction.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


/*  입찰
테이블 : bidding

컬럼 :
    id : 입찰 번호 (Primary Key, Auto Increment)
    bidder : 입찰자 (FK, Not Null)
    auction : 입찰한 경매 (FK, Not Null)
    price : 입찰 금액  (Not Null)
    bidTime : 입찰 시간 (Not Null)
    Status : 상태 (낙찰, 유찰, 진행)
*/
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bidding")
public class Bidding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nickname", referencedColumnName = "nickname" , nullable = false)
    private Member bidder;

    @ManyToOne
    @JoinColumn(name = "auction_id", nullable = false)
    private MusicAuction auctionId;

    @Column(nullable = false)
    private Long price;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime bidTime;

    @Column(nullable = false)
    private String Status;

    /*@PrePersist
    public void prePersist() {
        if (bidTime == null) {
            ZoneId kst = ZoneId.of("Asia/Seoul");
            bidTime = LocalDateTime.now(kst);
        }
    }*/

}