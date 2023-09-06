package com.pits.auction.auctionBoard.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishListDTO {

    private Long id;

    private String memberEmail;  // 회원 닉네임

    private Long auctionId;         // 경매 ID

    private LocalDateTime addedAt;  // 찜한 시간
}