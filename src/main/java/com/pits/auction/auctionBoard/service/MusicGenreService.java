package com.pits.auction.auctionBoard.service;

import com.pits.auction.auctionBoard.entity.MusicGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicGenreService {
    List<MusicGenre> findAllGenres();
}
