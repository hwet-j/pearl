package com.pits.auction.user.controller;

import com.pits.auction.auth.entity.Member;
import com.pits.auction.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RequestMapping("user")
@RequiredArgsConstructor
@Controller

public class FindEmailController {


    private final UserRepository userRepository;


  /*  @PostMapping("/updatePw")
    public String searchPassword(@RequestParam String email, Model model) {
        model.addAttribute(email);
        Optional<Member> memberOptional = userRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            model.addAttribute("success", true);
            model.addAttribute("email", email);
        } else {
            model.addAttribute("success", false);
            model.addAttribute("message", "일치하는 사용자를 찾을 수 없습니다.");
        }
        return "/user/updatePw";
    }*/




    @GetMapping("/email")
    public ResponseEntity<String> getEmailByPhoneNumber(@RequestParam String phoneNumber) {
        Optional<Member> memberOptional = userRepository.findByPhoneNumber(phoneNumber);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            String email = member.getEmail();
            return ResponseEntity.ok(email);
        } else {
            return ResponseEntity.notFound().build();
        }
    }














    @PostMapping("/user/password")
    public ResponseEntity<String> getPassword(@RequestParam String email) {
        // 여기에서 이메일을 사용하여 필요한 작업을 수행합니다.
        // 예를 들어, 데이터베이스에서 사용자를 찾고 비밀번호를 가져오는 작업을 수행할 수 있습니다.

        // 이 예제에서는 간단하게 이메일을 응답으로 다시 보내는 것으로 대체하겠습니다.
        return ResponseEntity.ok("서버에서 받은 이메일: " + email);
    }








}






