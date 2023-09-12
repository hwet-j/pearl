package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.repository.AdminMusicAuctionRepository;
import com.pits.auction.auth.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminMusicAuctionServiceImpl implements AdminMusicAuctionService{

    private final AdminMusicAuctionRepository adminMusicAuctionRepository;

    public Page<MusicAuction> getMusicAuctionList(Pageable pageable){
        Page<MusicAuction> musicAuctionList=adminMusicAuctionRepository.findAll(pageable);
        return musicAuctionList;
    }


    public void deleteMusicAuction(List<Long> ids) {
        for (Long id : ids) {
            adminMusicAuctionRepository.deleteById(id);
        }
    }




}
