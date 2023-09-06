package com.pits.auction.user.controller;


import com.pits.auction.auth.entity.Member;
import com.pits.auction.user.repository.UserRepository;
import com.pits.auction.user.service.UserSecurityService;
import com.pits.auction.user.service.UserService;
import com.pits.auction.user.validation.UserCreateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RequestMapping("user")
@RequiredArgsConstructor
@Controller

public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserSecurityService userSecurityService;




    private final PasswordEncoder passwordEncoder;

    @GetMapping("/findPassword")
    public String showFindPasswordPage() {
        return "findPassword";
    }



//로그인폼을 보여줘요청
    /*요청주소 /user/login
      요청방식 get */

    @GetMapping("/plLogin")
    public String login(){
        return "/user/plLogin";
    }







    @ModelAttribute("userCreateForm")
    public UserCreateForm getUserCreateForm() {
        return new UserCreateForm();
    }


    @GetMapping("/findId")
    public String findId(){
        return "/user/findId";
    }


    @GetMapping("/findPw")
    public String findPw(){
        return "/user/findPw";
    }




    @PostMapping("/plJoin2")
    public String join2(@Valid UserCreateForm userCreateForm,
                        BindingResult bindingResult, Model model) {

        System.out.println(userCreateForm.getPhoneNumber());

        if(bindingResult.hasErrors()){


            return "/user/plLogin";//templates폴더하위의 signup_form.html문서를 보여줘
        }
        try {

            userService.create(userCreateForm.getNickname(),
                    userCreateForm.getEmail(),
                    userCreateForm.getPassword1(),
                    userCreateForm.getPhoneNumber());
        }catch (DataIntegrityViolationException e){

            //여기에서는 username(회원id은 uk, email은 uk)->제약조건에 걸리면 발생
            e.printStackTrace();
            model.addAttribute("joinFailed", "이미 등록된 회원입니다.");
            return "/user/plLogin";// signup_form.html문서로 이동
        }catch (Exception e){

            e.printStackTrace();
            model.addAttribute("joinFailed", "이미 등록된 회원입니다.");

            return "/user/plLogin";
        }

        //3.Model //4.view
        return "redirect:/"; //회원가입성공시  메인화면으로 이동
    }











    }






















