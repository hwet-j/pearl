package com.pits.auction.auctionBoard.component;

import com.pits.auction.auctionBoard.dto.BiddingDTO;
import com.pits.auction.auctionBoard.entity.Bidding;
import org.springframework.stereotype.Component;


@Component
public class BiddingMapper {


    public BiddingDTO convertToDTO(Bidding bidding) {
        return BiddingDTO.builder()
               .id(bidding.getId())
                .bidder(bidding.getBidder().getNickname())
                .auctionId(bidding.getAuctionId().getId())
                .price(bidding.getPrice())
                .bidTime(bidding.getBidTime())
                .status(bidding.getStatus())
                .build();
    }


}
