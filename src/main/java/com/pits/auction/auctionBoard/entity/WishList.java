package com.pits.auction.auctionBoard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pits.auction.auth.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "wishlist")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가하는 PK 설정
    private Long id; // 자동 증가하는 단일 기본 키

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "member_email", referencedColumnName = "email", nullable = false)
    private Member memberEmail;

    @ManyToOne
    @JoinColumn(name = "auction_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MusicAuction auctionId;

    @Column(nullable = false)
    private LocalDateTime addedAt; // 찜한 시간

}
