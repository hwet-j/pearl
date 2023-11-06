
package com.pits.auction.user.controller;

import com.pits.auction.auth.entity.Member;
import com.pits.auction.user.service.UserService;
import com.pits.auction.user.validation.UserCreateForm2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            model.addAttribute("updateResult", "해당 이메일의 사용자를 찾을 수 없습니다.");
            model.addAttribute("email", email);  // 실패한 경우 이메일 값을 다시 모델에 추가
            return "/user/updatePw";
        }

        // 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인
        String newPassword1 = userCreateForm2.getNewPassword1();
        String newPassword2 = userCreateForm2.getNewPassword2();

        if (newPassword1.equals(newPassword2)) {
            // 새 비밀번호와 새 비밀번호 확인이 일치하는 경우
            String hashedPassword = passwordEncoder.encode(newPassword1);
            currentUser.setPassword(hashedPassword);

            // DB에 변경된 사용자 정보 저장
            userService.saveOrUpdate(currentUser);

            model.addAttribute("updateResult", "비밀번호가 업데이트되었습니다.");
            return "/user/updatePasswordPage";
        } else {
            // 새 비밀번호와 새 비밀번호 확인이 일치하지 않는 경우
            model.addAttribute("updateResult", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("email", email);  // 실패한 경우 이메일 값을 다시 모델에 추가
        }

        return "/user/updatePw";
    }





















}




