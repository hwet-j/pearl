package com.pits.auction.auctionBoard.repository;

import com.pits.auction.auctionBoard.entity.AuctionComment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionCommentRepository extends JpaRepository<AuctionComment, Long> {
}
