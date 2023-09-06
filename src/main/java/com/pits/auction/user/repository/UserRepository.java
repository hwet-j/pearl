package com.pits.auction.user.repository;
//Reprository는  Entity에 의하여 생성된 db에 접근하여
//작업하는 여러 메서드들로 구성된 interface이다
//=>CRUD


import com.pits.auction.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository인터페이스를 상속하고 있다
// <Repository의 대상이 되는 Entity타입,  Entity타입의 PK타입>
//여기에서는 <SiteUser,Integer> Repository의 대상이 되는 SiteUser의 PK는  Long
public interface UserRepository extends JpaRepository<Member,Long> {
    //(로그인)사용자조회
    Optional<Member> findByNickname(String nickname);


    Optional<Member> findByPhoneNumber(String phoneNumber);

    Optional<Member> findByEmail(String email);










}
