package com.pits.auction.auth.controller;

import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("id="+id);
        Member member=memberService.getMemberDetail(id);
        model.addAttribute("member",member);
        return "/admin/plAdminMemberEdit";
    }
    @PostMapping("/member/edit/{id}")
    public String AdminEditMember(@PathVariable("id") Long id, @ModelAttribute MemberDTO memberDTO) throws Exception{
        System.out.println("memberDTO="+memberDTO);
        memberService.AdminEditMember(memberDTO,id);
        return "redirect:/member/edit/"+id;
    }

    @GetMapping("/member/delete")
    public String memberDelete(@RequestParam("id")Long id) throws Exception {
        System.out.println("id="+id);
        memberService.deleteMember(id);
        return "redirect:/member/list";
    }

    @PostMapping("/member/delete")
    public String deleteMembers(@RequestParam("selectedMembers")List<Long> ids) throws Exception {
        System.out.println("id="+ids);
        memberService.deleteMembers(ids);
        return "redirect:/member/list";
    }


}
