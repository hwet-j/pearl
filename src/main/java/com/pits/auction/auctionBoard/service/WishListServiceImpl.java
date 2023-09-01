package com.pits.auction.auctionBoard.service;


import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.entity.WishList;
import com.pits.auction.auctionBoard.repository.MusicAuctionRepository;
import com.pits.auction.auctionBoard.repository.WishListRepository;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService{

    private final WishListRepository wishListRepository;
    private final MemberRepository memberRepository;
    private final MusicAuctionRepository musicAuctionRepository;


    @Override
    public void addWishList(String memberNickname, Long auctionId) {
        Optional<Member> optionalMember = memberRepository.findByNickname(memberNickname);

        MusicAuction auction = musicAuctionRepository.findById(auctionId)
                .orElseThrow(() -> new EntityNotFoundException("Auction not found"));

        WishList wishList = new WishList();

        if(optionalMember.isPresent()){
            wishList.setMemberNickname(optionalMember.get());
        }

        wishList.setAuctionId(auction);
        wishList.setAddedAt(LocalDateTime.now());


        wishListRepository.save(wishList);
    }

    @Override
    public Long countByMemberNicknameAndAuctionId(Member member, MusicAuction auction){
        wishListRepository.countByMemberNicknameAndAuctionId(member, auction);
        return null;
    }

}
