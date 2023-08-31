package com.pits.auction.auth.validation;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberEditValidator {

    private Long id;

    private String memberImage;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$|^$",
            message = "비밀번호는 최소 8자 길이, 대문자, 소문자, 숫자, 특수 문자가 포함되어야 합니다.")
    private String password;


    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$|^$",
            message = "전화번호 형식에 맞지 않습니다. (010-0000-0000)")
    private String phoneNumber;

}


/*
비밀번호 유효성 검사

(?=.*[a-z]): 소문자가 적어도 한 개 이상 포함되어야 함
(?=.*[A-Z]): 대문자가 적어도 한 개 이상 포함되어야 함
(?=.*\\d): 숫자가 적어도 한 개 이상 포함되어야 함
(?=.*[@$!%*?&]): 특수 문자가 적어도 한 개 이상 포함되어야 함
[A-Za-z\\d@$!%*?&]{8,}: 위 조건들을 모두 만족하며 최소 8자 이상이어야 함
*/