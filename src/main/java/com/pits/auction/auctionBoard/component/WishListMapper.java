package com.pits.auction.auctionBoard.component;

import com.pits.auction.auctionBoard.dto.WishListDTO;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.entity.WishList;
import com.pits.auction.auth.entity.Member;
import org.springframework.stereotype.Component;


@Component
public class WishListMapper {

    public WishListDTO entityToDto(WishList wishList) {
        return WishListDTO.builder()
                .id(wishList.getId())
                .memberEmail(wishList.getMemberEmail().getEmail())
                .auctionId(wishList.getAuctionId().getId())
                .addedAt(wishList.getAddedAt())
                .build();
    }

    public WishList dtoToEntity(WishListDTO wishListDTO) {
        Member member = Member.builder()
                .nickname(wishListDTO.getMemberEmail())
                .build();

        MusicAuction musicAuction = MusicAuction.builder()
                .id(wishListDTO.getAuctionId())
                .build();

        return WishList.builder()
                .id(wishListDTO.getId())
                .memberEmail(member)
                .auctionId(musicAuction)
                .addedAt(wishListDTO.getAddedAt())
                .build();
    }

}
