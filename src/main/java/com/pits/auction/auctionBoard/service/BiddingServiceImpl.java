package com.pits.auction.auctionBoard.service;


import com.pits.auction.auctionBoard.component.BiddingMapper;
import com.pits.auction.auctionBoard.dto.BiddingDTO;
import com.pits.auction.auctionBoard.entity.Bidding;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.repository.BiddingRepository;
import com.pits.auction.auctionBoard.repository.MusicAuctionRepository;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.repository.MemberRepository;
import com.pits.auction.global.exception.InsufficientBiddingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    private final BiddingMapper biddingMapper;

    /* 입찰 정보 저장 */
    @Override
    @Transactional
    public boolean biddingWrite(Bidding bidding) {

        // 0원 이하의 입찰 불가
        if (bidding.getPrice() <= 0) {
            throw new IllegalArgumentException("Invalid bidding amount: " + bidding.getPrice());
        }
        
        // 현재 최고가 보다 낮은 금액 입찰 불가
        if (getMaxBidPriceForAuction(bidding.getAuctionId().getId()) >= bidding.getPrice()) {
            throw new InsufficientBiddingException("Insufficient bidding amount: " + bidding.getPrice());
        }

        try {
            biddingRepository.save(bidding);
            return true;
        } catch (Exception e) {
            return false;
        }
    }




    /* 입찰 DTO -> Entity 형변환 (첫 생성에만 사용 - 사실상 입찰은 변경이 불가능 / BidTime, Status null값 받아오면 초기값 지정) */
    @Override
    public Bidding createBidding(BiddingDTO biddingDTO) {
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
                    .status(biddingDTO.getStatus() != null ? biddingDTO.getStatus() : "진행")
                    .build());
        } else {
            // new EntityNotFoundException("Member not found with nickname: " + biddingDTO.getBidder()));
            return null;
        }
    }



    /* ID값으로 입찰기록 가져오기 */
    @Override
    public BiddingDTO findById(Long id) {
        Optional<Bidding> optionalBidding = biddingRepository.findById(id);
        if (optionalBidding.isPresent()){
            Bidding bidding = optionalBidding.get();
            return biddingMapper.convertToDTO(bidding);
        }
        return null;
    }


    @Override
    /* 경매 물품(음악)에 따른 입찰 목록 */
    public List<Bidding> getAuctionBiddingsById(Long auctionId) {
        MusicAuction musicAuction = musicAuctionRepository.findById(auctionId)
                .orElseThrow(() -> new EntityNotFoundException("Auction not found with id: " + auctionId));

        return musicAuction.getAuctionBiddings();
    }

    /* 입찰 전체목록 */
    @Override
    public List<Bidding> getAuctionBiddings() {
        return biddingRepository.findAll();
    }


    @Override
    public Long getMaxBidPriceForAuction(Long auctionId) {
        Optional<Long> maxBidPrice = biddingRepository.findMaxPriceByAuctionId(auctionId);
        return maxBidPrice.orElse(null); // 0L 또는 원하는 기본 값으로 변경
    }
}
