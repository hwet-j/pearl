package com.pits.auction.global.exception;

public class PhoneNumberDuplicateException extends RuntimeException {
    public PhoneNumberDuplicateException(String phoneNumber) {
        super("전화번호 " + phoneNumber + "는 중복된 정보입니다.");
    }
}