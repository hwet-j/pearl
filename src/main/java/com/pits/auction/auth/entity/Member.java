package com.pits.auction.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pits.auction.auctionBoard.entity.Bidding;
import com.pits.auction.auctionBoard.entity.MusicAuction;
import com.pits.auction.auctionBoard.entity.WishList;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


/*  회원
테이블 : member

컬럼 :
    id : 회원 번호 (Primary Key, Auto Increment)
    memberImage : 회원 이미지
    email : 회원 이메일 (Unique, Not Null)
    password : 회원 비밀번호
    nickname : 회원 닉네임 (Unique, Not Null)
    phoneNumber : 회원 전화번호 (Unique)
    balance : 잔고
    birthdate : 생년월일
*/

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberImage;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(unique = true)
    private String phoneNumber;

    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private Long balance;

    // 회원 탈퇴 요청 여부 --> 회원이 삭제되면 입찰정보, 글정보 전부 삭제되므로 회원이 삭제한다고 삭제 불가능하게 하기 위해(관리자가 최종 제거)
    private Boolean withdrawalRequested;


    /* 다른 테이블과 관계 설정 */
    
    // 해당 회원의 입찰 정보
    @JsonIgnore
    @OneToMany(mappedBy = "bidder", cascade = CascadeType.REMOVE)
    private List<Bidding> biddings;

    // 해당 회원의 경매글 정보
    @JsonIgnore
    @OneToMany(mappedBy = "authorNickname", cascade = CascadeType.REMOVE)
    private List<MusicAuction> musicAuctions;

    // 찜목록
    @JsonIgnore
    @OneToMany(mappedBy = "memberNickname")
    private List<WishList> wishList;


}