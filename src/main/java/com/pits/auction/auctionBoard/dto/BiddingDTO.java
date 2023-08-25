package com.pits.auction.auctionBoard.dto;


import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auth.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BiddingDTO {

    private Long id;
    private Member bidder;
    private MusicAuction auctionId;
    private Long price;
    private LocalDateTime bidTime;
    private String Status;


}
