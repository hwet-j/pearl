package com.pits.auction.user.validation;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

//SiteUser Entity관련
//회원가입폼페이지에서 입력.선택사항에 적용되는 유효성검사 클래스
public class UserCreateForm {



    @NotEmpty(message = "닉네임은 필수입력입니다.")
   // @Pattern(regexp = "^(?!.*admin).*", message = "닉네임에 'admin'을 포함할 수 없습니다.")
    private String nickname;    //회원이름.uk

    @NotEmpty(message = "비밀번호는 필수입력입니다.")
    private String password1;   //비밀번호

    @NotEmpty(message = "비밀번호 확인은 필수입력입니다.")
    private String password2;   //(form.html문서에 존재하는 )확인용 비밀번호

   /* @NotEmpty(message = "비밀번호는 필수입력입니다.")
    private String newPassword1;   //비밀번호

    @NotEmpty(message = "비밀번호 확인은 필수입력입니다.")
    private String newPassword2;   //(form.html문서에 존재하는 )확인용 비밀번호*/

    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;   //이메일.uk

    @NotEmpty(message = "전화번호는 필수입력입니다.")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$|^$",
            message = "전화번호 형식에 맞지 않습니다. (010-0000-0000)")
    private String phoneNumber;


    @AssertTrue(message = "비밀번호와 비밀번호 확인이 일치하지 않습니다.")
    public boolean isPasswordConfirmed() {
        return password1 != null&&password1.equals(password2);
    }



}

