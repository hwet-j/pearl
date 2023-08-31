package com.pits.auction.auctionBoard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pits.auction.auth.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bidding")
public class Bidding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "nickname", referencedColumnName = "nickname" , nullable = false)
    private Member bidder;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "auction_id", nullable = false)
    private MusicAuction auctionId;

    @Column(nullable = false)
    private Long price;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, updatable = false)
    private LocalDateTime bidTime;

    @Column(nullable = false)
    private String status;

}