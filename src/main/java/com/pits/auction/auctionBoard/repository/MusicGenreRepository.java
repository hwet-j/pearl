package com.pits.auction.auctionBoard.repository;

import com.pits.auction.auctionBoard.entity.MusicGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicGenreRepository extends JpaRepository<MusicGenre, Long> {
    MusicGenre findByName(MusicGenre genreName);
}
