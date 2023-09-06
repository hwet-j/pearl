
package com.pits.auction.user.controller;

import com.pits.auction.auth.entity.Member;
import com.pits.auction.user.service.UserService;
import com.pits.auction.user.validation.UserCreateForm;
import com.pits.auction.user.validation.UserCreateForm2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class PasswordUpdateController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/user/updatePw")
    public String showUpdatePasswordForm(Model model, @RequestParam String email) {
        // 업데이트 폼을 보여줄 때 필요한 데이터를 모델에 추가
        model.addAttribute("email", email);
        model.addAttribute("userCreateForm2", new UserCreateForm2());
        return "/user/updatePw"; // 업데이트 폼을 보여주는 HTML 페이지
    }

    @PostMapping("/user/updatePw")
    public String updatePassword(@ModelAttribute("userCreateForm2") UserCreateForm2 userCreateForm2,
                                 @RequestParam(name = "email") String email,
                                 Model model) {

        // 주어진 이메일 파라미터를 기반으로 사용자를 찾음
        Member currentUser = userService.findByEmail(email);
        if (currentUser == null) {
            // 주어진 이메일로 사용자를 찾을 수 없는 경우
            model.addAttribute("updateResult", "해당 이메일의 사용자를 찾을 수 없습니다.");
            return "/user/updatePasswordPage";
        }

        // 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인
        String newPassword1 = userCreateForm2.getNewPassword1();
        String newPassword2 = userCreateForm2.getNewPassword2();

        if (newPassword1.equals(newPassword2)) {
            // 새 비밀번호와 새 비밀번호 확인이 일치하는 경우
            // 비밀번호를 해싱하여 업데이트
            String hashedPassword = passwordEncoder.encode(newPassword1);
            currentUser.setPassword(hashedPassword);

            // DB에 변경된 사용자 정보 저장
            userService.saveOrUpdate(currentUser);

            model.addAttribute("updateResult", "비밀번호가 업데이트되었습니다.");
        } else {
            // 새 비밀번호와 새 비밀번호 확인이 일치하지 않는 경우
            // 업데이트 결과를 모델에 추가
            model.addAttribute("updateResult", "새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.");
        }

        return "/user/updatePasswordPage"; // 업데이트 결과를 보여주는 HTML 페이지
    }






















}




