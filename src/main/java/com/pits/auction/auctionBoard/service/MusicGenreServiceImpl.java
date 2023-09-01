package com.pits.auction.auctionBoard.service;


import com.pits.auction.auctionBoard.entity.MusicGenre;
import com.pits.auction.auctionBoard.repository.MusicGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicGenreServiceImpl implements MusicGenreService {
    @Autowired
    private MusicGenreRepository musicGenreRepository;

    public List<MusicGenre> findAllGenres() {
        return musicGenreRepository.findAll();
    }

    }
