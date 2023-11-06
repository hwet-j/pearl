package com.pits.auction.auctionBoard.entity;


import com.pits.auction.auth.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // PK

    @ManyToOne
    @JoinColumn(name = "auction_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MusicAuction musicAuction;  // 경매글 번호

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "author_nickname", referencedColumnName = "nickname", nullable = false)
    private Member authorNickname;      // 댓글을 작성한 회원

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createdAt;    // 댓글 작성 시간

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;             // 댓글 내용

}
