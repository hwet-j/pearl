package com.pits.auction.global.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
public class ExceptionController {

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException() {
        return "error/notadmin"; // 액세스 거부 시 에러 페이지로 이동
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("accessDenied", false);
    }
}
