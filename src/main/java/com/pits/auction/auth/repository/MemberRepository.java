package com.pits.auction.auth.repository;

import com.pits.auction.auctionBoard.entity.Bidding;
import com.pits.auction.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
   @Query("SELECT m FROM Member m WHERE m.withdrawalRequested = true")
    List<Member> findByWithdrawalRequestedTrue();
}
