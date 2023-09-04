package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.dto.MusicAuctionDTO;
import com.pits.auction.auctionBoard.dto.MusicAuctionDTO2;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.repository.BiddingPeriodRepository;
import com.pits.auction.auctionBoard.repository.BiddingRepository;
import com.pits.auction.auctionBoard.repository.MusicAuctionRepository;
import com.pits.auction.auctionBoard.repository.MusicGenreRepository;
import com.pits.auction.auctionBoard.service.MusicAuctionService;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicAuctionServiceImpl implements MusicAuctionService {
    private final MusicAuctionRepository musicAuctionRepository;
    private final MusicGenreRepository musicGenreRepository;
    private final BiddingPeriodRepository biddingPeriodRepository;
    private final MemberRepository memberRepository;
    private final BiddingRepository biddingRepository;
    private final ModelMapper modelMapper;

    @Override
    public boolean saveMusicAuction(MusicAuctionDTO2 musicAuctionDTO) {
        Member member = memberRepository.findByNickname(musicAuctionDTO.getAuthorNickname())
                .orElseThrow(() -> new IllegalArgumentException("No member with nickname: " + musicAuctionDTO.getAuthorNickname()));

        MusicAuction musicAuction = MusicAuction.builder()
                .genre(musicGenreRepository.findById(musicAuctionDTO.getGenre()).orElse(null))
                .albumImage(musicAuctionDTO.getAlbumImagePath())
                .albumMusic(musicAuctionDTO.getAlbumMusicPath())
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


    @Override
    public MusicAuctionDTO getMusicAuctionById(Long id) {
        Optional<MusicAuction> optionalMusicAuction = musicAuctionRepository.findById(id);

        if(optionalMusicAuction.isPresent()){
            MusicAuction musicAuction = optionalMusicAuction.get();
            return MusicAuctionDTO.fromEntity(musicAuction);
        }

        return null;
    }

    @Override
    public List<MusicAuction> findAll() {
        return musicAuctionRepository.findAll();
    }

    @Override
    public Optional<MusicAuction> findById(long id) {
        return musicAuctionRepository.findById(id);
    }

    @Override
    public Long remainingTime(LocalDateTime endTime){
        long currentTimeMillis = System.currentTimeMillis();

        long specificTimeMillis = endTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        if ((specificTimeMillis - currentTimeMillis) < 0){
            return null;
        }

        return (specificTimeMillis - currentTimeMillis) / 1000;
    }


    @Override
    public MusicAuctionDTO getLastBiddingAuction(String nickname){

        Long auctionId = biddingRepository.findLastAuctionIdByNickname(nickname);
        if (auctionId == null){
            return null;
        }

        Optional<MusicAuction> musicAuction = musicAuctionRepository.findById(auctionId);

        if (musicAuction.isPresent()){
            return MusicAuctionDTO.fromEntity(musicAuction.get());
        }
        return null;
    }

    @Override
    public Long findLastBidPriceByNickname(String nickname){
        return biddingRepository.findLastBidPriceByNickname(nickname);
    }


    @Override
    public Page<MusicAuction> getMusicByOrderByIdDesc(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return musicAuctionRepository.findAllByOrderByIdDesc(pageable);
    }


    @Override
    public List<MusicAuction> findAllByOrderByEndTime() {
        List<MusicAuction> musicAuctions=musicAuctionRepository.findAllByOrderByEndTime();
        return musicAuctions;
    }

    @Override
    @Transactional
    public boolean updateStatus(Long id) {
        Optional<MusicAuction> optionalMusicAuction =  musicAuctionRepository.findById(id);

        if(optionalMusicAuction.isPresent()){
            MusicAuction musicAuction = optionalMusicAuction.get();
            if(LocalDateTime.now().isAfter(musicAuction.getEndTime())){
                musicAuctionRepository.updateStatusById(id, "종료");
                return true;
            }
        }


        return false;
    }


    @Override
    public MusicAuctionDTO2 findDetailById(Long id) {
        Optional<MusicAuction> optionalAuction = musicAuctionRepository.findById(id);
        if(!optionalAuction.isPresent()) {
            throw new EntityNotFoundException("Auction not found with id: " + id);
        }

        MusicAuction auction = optionalAuction.get();
        MusicAuctionDTO2 dto = new MusicAuctionDTO2();

        dto.setGenre(auction.getGenre().getId()); // Genre의 ID를 가져옵니다.
        dto.setTitle(auction.getTitle());
        dto.setAlbumImagePath(auction.getAlbumImage());
        dto.setAlbumMusicPath(auction.getAlbumMusic());
        dto.setContent(auction.getContent());
        dto.setAuthorNickname(auction.getAuthorNickname().getNickname()); // Nickname 가져오는 메서드가 필요
        dto.setStartingBid(auction.getStartingBid());
        dto.setBiddingPeriod(auction.getBiddingPeriod().getId());

        // 장르 이름 설정
        if (auction.getGenre() != null) {
            dto.setGenreName(auction.getGenre().getName());
        }

        return dto;
    }
}
