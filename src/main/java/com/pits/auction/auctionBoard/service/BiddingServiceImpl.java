package com.pits.auction.auctionBoard.service;


import com.pits.auction.auctionBoard.dto.BiddingDTO;
import com.pits.auction.auctionBoard.entity.Bidding;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.repository.BiddingRepository;
import com.pits.auction.auctionBoard.repository.MusicAuctionRepository;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BiddingServiceImpl implements BiddingService{

    private final BiddingRepository biddingRepository;
    private final MemberRepository memberRepository;
    private final MusicAuctionRepository musicAuctionRepository;


    /* 입찰 정보 저장 */
    @Override
    public boolean biddingWrite(Bidding bidding) {
        try {
            biddingRepository.save(bidding);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* 입찰 DTO -> Entity 형변환 (첫 생성에만 사용 - 사실상 입찰은 변경이 불가능 / BidTime, Status null값 받아오면 초기값 지정) */
    public Bidding convertToEntity(BiddingDTO biddingDTO) {
        Optional<Member> memberOptional = memberRepository.findByNickname(biddingDTO.getBidder());
        Optional<MusicAuction> musicAuctionOptional = musicAuctionRepository.findById(biddingDTO.getAuctionId());

        if (memberOptional.isPresent() && musicAuctionOptional.isPresent()) {
            Member member = memberOptional.get();
            MusicAuction musicAuction = musicAuctionOptional.get();

            return biddingRepository.save(Bidding.builder()
                    .id(biddingDTO.getId())
                    .bidder(member)
                    .auctionId(musicAuction)
                    .price(biddingDTO.getPrice())
                    .bidTime(biddingDTO.getBidTime() != null ? biddingDTO.getBidTime() : LocalDateTime.now())
                    .Status(biddingDTO.getStatus() != null ? biddingDTO.getStatus() : "진행")
                    .build());
        } else {
            return null;
        }
    }


    /* 경매 물품(음악)에 따른 입찰 목록 */
    public List<Bidding> getAuctionBiddingsById(Long auctionId) {
        MusicAuction musicAuction = musicAuctionRepository.findById(auctionId)
                .orElseThrow(() -> new EntityNotFoundException("Auction not found with id: " + auctionId));

        return musicAuction.getAuctionBiddings();
    }

    /* 입찰 전체목록 */
    public List<Bidding> getAuctionBiddings() {
        return biddingRepository.findAll();
    }



}
