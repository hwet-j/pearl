package com.pits.auction.auctionBoard.repository;

import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.entity.WishList;
import com.pits.auction.auth.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

    long countByMemberEmailAndAuctionId(Member memberEmail, MusicAuction auctionId);


    @Transactional
    @Modifying
    @Query("DELETE FROM WishList w WHERE w.memberEmail = ?1 AND w.auctionId = ?2")
    void deleteByMemberEmailAndAuctionId(Member memberEmail, MusicAuction auctionId);


    List<WishList> findByMemberEmailEmail(String email);


}
