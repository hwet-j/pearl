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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService{

    private final WishListRepository wishListRepository;
    private final MemberRepository memberRepository;
    private final MusicAuctionRepository musicAuctionRepository;


    @Override
    public String clickWishButton(String email, Long auctionId) {

        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        MusicAuction auction = musicAuctionRepository.findById(auctionId)
                .orElseThrow(() -> new EntityNotFoundException("Auction not found"));


        WishList wishList = new WishList();

        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
            if (countByMemberNicknameAndAuctionId(member,auction) == 0){        // 해당 회원이 특정 경매글에 대한 찜 정보가 없으면 기능 찜 등록 수행
                wishList.setMemberEmail(member);
                wishList.setAuctionId(auction);
                wishList.setAddedAt(LocalDateTime.now());
                wishListRepository.save(wishList);

                return "Add";
            } else if (countByMemberNicknameAndAuctionId(member,auction) == 1){ // 찜 등록되어있으면 삭제
                wishListRepository.deleteByMemberEmailAndAuctionId(member,auction);
                return "Delete";
            } else {
                return "Fail";
            }

        }
        return "Error";

    }

    @Override
    public long countByMemberNicknameAndAuctionId(Member member, MusicAuction auction){
        return wishListRepository.countByMemberEmailAndAuctionId(member, auction);
    }

    @Override
    public List<WishList> findByMemberEmailEmail(String email) {
        return wishListRepository.findByMemberEmailEmail(email);
    }

    @Override
    public List<MusicAuction> getMusicAuctionsByEmail(String email) {
        List<WishList> wishLists = wishListRepository.findByMemberEmailEmail(email);
        List<MusicAuction> musicAuctions = new ArrayList<>();

        for (WishList wishList : wishLists) {
            MusicAuction musicAuction = wishList.getAuctionId();
            if (musicAuction != null) {
                musicAuctions.add(musicAuction);
            }
        }

        return musicAuctions;
    }

    @Override
    public Long countByEmailAndAuctionId(Long auctionId, String email) {
        Long wishExist;
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        Optional<MusicAuction> optionalMusicAuction = musicAuctionRepository.findById(auctionId);
        if(optionalMember.isPresent() && optionalMusicAuction.isPresent()){
            Member member = optionalMember.get();
            MusicAuction musicAuction = optionalMusicAuction.get();
            wishExist = countByMemberNicknameAndAuctionId(member,musicAuction);
        }else{
            wishExist = 0L;
        }
        return wishExist;
    }

}
