package com.pits.auction.user.service;


import com.pits.auction.auth.entity.Member;
import com.pits.auction.exception.DataNotFoundException;
import com.pits.auction.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    /*우리는  SecurityConfig.java에서 PasswordEncoder을 Bean등록해두었다
참고 BCryptPasswordEncoder클래스는 스프링 시큐리티에서 제공되는 클래스이다.
    이 클래스이용해서  패스워드를 암호화해서 처리하도록 한다.
    bcrypt는 패스워크드를 저장하는 용도로 설계된 해시 함수로
    특정 문자열을 암호화하고,
    체크하는 쪽에서는 암호화된 패스워드가 가능한 패스워드인지만 확인하고
    다시 원문으로 되돌리지는 못한다.(교재 p651참고)*/




    //회원가입처리
    public Member create(String nickname, String email, String password, String phoneNumber){
        Member member = new Member();
        member.setNickname(nickname);
        member.setEmail(email);
        member.setPhoneNumber(phoneNumber);
        //암호는 스프링시큐리티를 이용해서 암호화하여 비번을 저장
        member.setPassword(passwordEncoder.encode(password));
        userRepository.save(member);
        return member;
    }

    // user정보조회
    public Member getUser(String nickname) {
        Optional<Member> member = userRepository.findByNickname(nickname);
        if(member.isPresent()){
            return member.get();
        } else {
            throw new DataNotFoundException("member NOT FOUND");
        }

    }
    public Member findByEmail(String email){
        Optional<Member> optionalMember = userRepository.findByEmail(email);
        if(optionalMember.isPresent()){
            return optionalMember.get();
        }
        return userRepository.findByEmail(email).orElse(null);
    }




    public void saveOrUpdate(Member member) {
        userRepository.save(member);
    }




    public boolean updatePassword(String email, String newPassword1) {
        Optional<Member> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            Member member = userOptional.get();

            // 여기서 새 비밀번호를 해시화하거나 다른 보안 절차를 수행해야 합니다.
            // 이 예제에서는 간단히 새 비밀번호를 저장합니다.
            member.setPassword(newPassword1);

            // 변경된 비밀번호를 데이터베이스에 저장
            userRepository.save(member);

            return true; // 비밀번호 업데이트 성공
        }

        return false; // 사용자를 찾을 수 없는 경우 또는 업데이트 실패
    }
}













