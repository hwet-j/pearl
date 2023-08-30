package com.pits.auction.global.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(InsufficientBalanceException.class)
    public String handleInsufficientBalanceException(InsufficientBalanceException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/insufficientBalance"; // 예외 페이지로 이동
    }

    @ExceptionHandler(InsufficientBiddingException.class)
    public String handleInsufficientBiddingException(InsufficientBiddingException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/insufficientBalance"; // 예외 페이지로 이동
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/illegalArgument"; // 예외 페이지로 이동
    }


    @ExceptionHandler(PhoneNumberDuplicateException.class)
    public String handlePhoneNumberDuplicateException(PhoneNumberDuplicateException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/duplicateError"; // 예외 페이지로 이동
    }

}
