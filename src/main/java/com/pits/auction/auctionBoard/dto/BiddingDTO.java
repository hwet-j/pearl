package com.pits.auction.auctionBoard.dto;


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
    private String bidder;
    private Long auctionId;
    private Long price;
    private LocalDateTime bidTime;
    private String Status;

}
