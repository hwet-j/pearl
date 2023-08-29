package com.pits.auction.auctionBoard.component;

import com.pits.auction.auctionBoard.dto.BiddingDTO;
import com.pits.auction.auctionBoard.dto.MusicAuctionDTO;
import com.pits.auction.auctionBoard.entity.Bidding;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auth.entity.Member;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;


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
