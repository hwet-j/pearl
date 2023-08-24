package com.pits.auction.auth.repository;

import com.pits.auction.auctionBoard.entity.Bidding;
import com.pits.auction.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
