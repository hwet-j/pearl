package com.pits.auction.auctionBoard.service;


import com.pits.auction.auctionBoard.dto.MusicGenreDTO;
import com.pits.auction.auctionBoard.entity.MusicGenre;
import com.pits.auction.auctionBoard.repository.MusicGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicGenreServiceImpl implements MusicGenreService {
    @Autowired
    private MusicGenreRepository musicGenreRepository;

    public List<MusicGenre> findAllGenres() {
        return musicGenreRepository.findAll();
    }



    @Override
    public MusicGenreDTO findById(Long id) {

        Optional<MusicGenre> optionalMusicGenre = musicGenreRepository.findById(id);
        if(optionalMusicGenre.isPresent()){
            MusicGenreDTO musicGenreDTO = new MusicGenreDTO(optionalMusicGenre.get().getId(),optionalMusicGenre.get().getName() );
            return musicGenreDTO;
        }

        return null;
    }

}
