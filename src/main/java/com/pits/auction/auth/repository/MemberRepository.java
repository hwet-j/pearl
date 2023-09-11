package com.pits.auction.auth.repository;

import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByPhoneNumber(String phoneNumber);

    @Query("SELECT m FROM Member m WHERE m.withdrawalRequested = false")
    List<Member> findAllActiveMembers();

    @Query("SELECT m FROM Member m WHERE m.withdrawalRequested = true AND m.email = :email")
    Optional<Member> findActiveMembersByEmail(@Param("email") String email);

    @Query("SELECT m FROM Member m WHERE m.withdrawalRequested = true")
    Page<Member> findByWithdrawalRequestedTrue(Pageable pageable);
    Page<Member> findAll(Pageable pageable);

    Optional<Member> findByEmail(String currentUserEmail);
}