package com.pits.auction.global.exception;

public class InsufficientBiddingException extends RuntimeException {
    public InsufficientBiddingException(String message) {
        super(message);
    }
}