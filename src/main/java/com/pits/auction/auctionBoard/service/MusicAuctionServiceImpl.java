package com.pits.auction.auctionBoard.service;


import com.pits.auction.auctionBoard.dto.MusicAuctionDTO;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.repository.MusicAuctionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicAuctionServiceImpl implements MusicAuctionService{

    private final MusicAuctionRepository musicAuctionRepository;
    private final ModelMapper modelMapper;

    @Override
    public MusicAuctionDTO getMusicAuctionById(Long id) {
        Optional<MusicAuction> optionalMusicAuction = musicAuctionRepository.findById(id);

        if(optionalMusicAuction.isPresent()){
            MusicAuction musicAuction = optionalMusicAuction.get();
            return MusicAuctionDTO.fromEntity(musicAuction);
        }

        return null;
    }





}
