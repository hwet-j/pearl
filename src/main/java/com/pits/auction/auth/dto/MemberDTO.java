package com.pits.auction.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {

    private Long id;
    private String memberImage;
    private String password;
    private String email;
    private String nickname;
    private String phoneNumber;
    private Long balance;
    private Boolean withdrawalRequested;

}
