package com.pits.auction.user.service;


import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.repository.MemberRepository;
import com.pits.auction.user.repository.UserRepository;
import com.pits.auction.user.validation.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//spring security에 등록할 클래스
@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    //사용자이름으로 (비밀번호)조회
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> optionalMember = userRepository.findByEmail(email);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        // 사용자 아이디는 찾았지만 회원 정보의 탈퇴요청이 true라면
        Member siteUser = optionalMember.get();
        if (memberRepository.findActiveMembersByEmail(email).isPresent()){
            throw new UsernameNotFoundException("사용자는 탈퇴한 회원입니다.");
        }


        List<GrantedAuthority> authorities = new ArrayList<>();
        //nickname "admin"을 포함하면
        if (siteUser.getNickname().contains("admin")) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
            System.out.println("UserRole.ADMIN.getValue())"+UserRole.ADMIN.getValue());
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
            System.out.println("UserRole.USER.getValue())"+UserRole.USER.getValue());
        }
        //사용자User객체리턴
        return new User(email, siteUser.getPassword(), authorities);
        //스크링시큐리티는  loadUserByUsername()에 의해 리턴되는 User객체의 비밀번호가
        // 화면으로부터 입력한 비번와 일치하는지 검사하는 로직이 내부적으로 존재
    }


    @Transactional(readOnly = true)
    public String getEmailByPhoneNumber(String phoneNumber) {
        Optional<Member> member = userRepository.findByPhoneNumber(phoneNumber);


        if (member.isPresent()) {
            return member.get().getEmail();
        }

        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }


    @Transactional(readOnly = true)
    public String getPasswordByEmail(String email) {
        Optional<Member> member = userRepository.findByEmail(email);


        if (member.isPresent()) {
            return member.get().getPassword();
        }

        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }

}