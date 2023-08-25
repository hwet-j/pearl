package com.pits.auction.auth.controller;

import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@RequiredArgsConstructor
@Controller
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/member/list")
    public String memberList(Model model) throws Exception {
        List<Member> memberList = this.memberService.getMemberList();
        model.addAttribute("memberList",memberList);
        return "/admin/plAdminMemberList";
    }
    @GetMapping("/member/edit/{id}")
    public String memberDetail(@PathVariable("id")Long id,Model model) throws Exception {
        Member member=memberService.getMemberDetail(id);
        model.addAttribute("member",member);
        System.out.println("id="+id);
        System.out.println("member="+member);
        return "/admin/plAdminMemberEdit";
    }

    @GetMapping("/member/delete")
    public String memberDelete(@RequestParam("id")Long id) throws Exception {
        System.out.println("id="+id);
        memberService.deleteMember(id);
        return "redirect:/member/list";
    }

}
