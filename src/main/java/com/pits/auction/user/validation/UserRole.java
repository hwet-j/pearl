package com.pits.auction.user.validation;

import lombok.Getter;

//(스프링시큐리티가) 인증 후 사용자에게 부여할 권한을 정의한 클래스
@Getter
public enum UserRole {
    //ADMIN은 "ROLE_ADMIN"을 값으로,
    //USER는 "ROLE_USER"을 값으로가진다
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String value;
    UserRole(String value){
        this.value = value;
    }

}
