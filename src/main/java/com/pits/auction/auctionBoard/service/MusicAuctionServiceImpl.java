package com.pits.auction.auctionBoard.service;


import com.pits.auction.auctionBoard.dto.MusicAuctionDTO;
import com.pits.auction.auctionBoard.entity.BiddingPeriod;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.entity.MusicGenre;
import com.pits.auction.auctionBoard.repository.BiddingPeriodRepository;
import com.pits.auction.auctionBoard.repository.MusicAuctionRepository;
import com.pits.auction.auctionBoard.repository.MusicGenreRepository;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MusicAuctionServiceImpl implements MusicAuctionService {
    private final MusicAuctionRepository musicAuctionRepository;
    private final MusicGenreRepository musicGenreRepository;
    private final BiddingPeriodRepository biddingPeriodRepository;
    private final MemberRepository memberRepository;

    @Override
    public boolean saveMusicAuction(MusicAuctionDTO musicAuctionDTO) {
        Member member = memberRepository.findByNickname(musicAuctionDTO.getAuthorNickname())
                .orElseThrow(() -> new IllegalArgumentException("No member with nickname: " + musicAuctionDTO.getAuthorNickname()));

        MusicAuction musicAuction = MusicAuction.builder()
                .genre(musicGenreRepository.findById(musicAuctionDTO.getGenre()).orElse(null))
                .albumImage(musicAuctionDTO.getAlbumImage().getOriginalFilename())
                .albumMusic(musicAuctionDTO.getAlbumMusic().getOriginalFilename())
                .content(musicAuctionDTO.getContent())
                .title(musicAuctionDTO.getTitle())
                .authorNickname(member)
                .startingBid(musicAuctionDTO.getStartingBid())
                .biddingPeriod(biddingPeriodRepository.findById(musicAuctionDTO.getBiddingPeriod()).orElse(null)) // 여기서 findById를 사용하여 입찰 기간을 가져옵니다.
                .status("진행")
                .build();

        System.out.println(musicAuction.getBiddingPeriod().getPeriodValue());
        System.out.println(musicAuction.getBiddingPeriod().getId());
        System.out.println(musicAuction.getStatus());
        musicAuctionRepository.save(musicAuction);
        // DB에 저장
        return true;
    }
}

