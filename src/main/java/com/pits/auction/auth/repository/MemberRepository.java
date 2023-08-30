package com.pits.auction.auth.repository;

import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByPhoneNumber(String phoneNumber);

    @Query("SELECT m FROM Member m WHERE m.withdrawalRequested = false")
    List<Member> findAllActiveMembers();

    Optional<Member> findByNickname(String nickname);

}
