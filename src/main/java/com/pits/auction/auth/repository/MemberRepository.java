package com.pits.auction.auth.repository;

import com.pits.auction.auth.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
   @Query("SELECT m FROM Member m WHERE m.withdrawalRequested = true")
    Page<Member> findByWithdrawalRequestedTrue(Pageable pageable);
    Page<Member> findAll(Pageable pageable);
}
