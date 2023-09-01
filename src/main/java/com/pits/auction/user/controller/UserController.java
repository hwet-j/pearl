package com.pits.auction.user.controller;


import com.pits.auction.user.service.UserService;
import com.pits.auction.user.validation.UserCreateForm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("user")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;




    //로그인폼을 보여줘요청
    /*요청주소 /user/login
      요청방식 get */

    @GetMapping("/plLogin")
    public String login(){
        return "/user/plLogin";
    }


    @GetMapping("/plJoin")
    public String join(){
        return "/user/plJoin";
    }


    @ModelAttribute("userCreateForm")
    public UserCreateForm getUserCreateForm() {
        return new UserCreateForm();
    }




    @PostMapping("/plJoin")
    public String join2(@Valid UserCreateForm userCreateForm,
                        BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){

            return "/user/plJoin";//templates폴더하위의 signup_form.html문서를 보여줘
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
            return "/user/plJoin";// signup_form.html문서로 이동
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("joinFailed", "이미 등록된 회원입니다.");

            return "/user/plJoin";
        }

        //3.Model //4.view
        return "redirect:/"; //회원가입성공시  메인화면으로 이동
    }
}




