package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.repository.AdminMusicAuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AdminMusicAuctionServiceImpl implements AdminMusicAuctionService{

    private final AdminMusicAuctionRepository adminMusicAuctionRepository;
    public List<MusicAuction> getMusicAuctionList(){
        List<MusicAuction> musicAuctionList=adminMusicAuctionRepository.findAll();
        return musicAuctionList;
    }
    public void deleteMusicAuction(List<Long> ids) {
        for (Long id : ids) {
            adminMusicAuctionRepository.deleteById(id);
        }
    }

}
