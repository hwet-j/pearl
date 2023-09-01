package com.pits.auction.auth.controller;

import com.pits.auction.auth.dto.MemberDTO;
import com.pits.auction.auth.entity.Member;
import com.pits.auction.auth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminMemberController {

    private final MemberService memberService;

    //회원목록
    @GetMapping("/member/list")
    public String memberList(Model model,@PageableDefault(size = 10) Pageable pageable) throws Exception {
        Page<Member> memberList = memberService.getMemberList(pageable);
       int nowPage=memberList.getPageable().getPageNumber()+1;
       int startPage=Math.max(nowPage-3,1);
       int endPage=Math.min(nowPage+3,memberList.getTotalPages());
       int firstPage=Math.max(0,0);
       int lastPage=memberList.getTotalPages()-1;

        model.addAttribute("memberList",memberList);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("firstPage",firstPage);
        model.addAttribute("lastPage",lastPage);

        return "/admin/plAdminMemberList";
    }

    //탈퇴회원 리스트
    @GetMapping("/member/Ylist")
    public String memberYList(Model model,@PageableDefault(size = 10) Pageable pageable) throws Exception {
       Page<Member> memberYList = memberService.getMemberYList(pageable);
        int nowPage=memberYList.getPageable().getPageNumber()+1;
        int startPage=Math.max(nowPage-3,1);
        int endPage=Math.min(nowPage+3,memberYList.getTotalPages());
        int firstPage=Math.max(0,0);
        int lastPage=memberYList.getTotalPages()-1;
        model.addAttribute("memberYList",memberYList);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("firstPage",firstPage);
        model.addAttribute("lastPage",lastPage);
        return "/admin/plAdminMemberYList";
    }

    //회원 상세페이지
    @GetMapping("/member/edit/{id}")
    public String memberDetail(@PathVariable("id")Long id,Model model) throws Exception {
        System.out.println("id="+id);
        Member member=memberService.getMemberDetail(id);
        model.addAttribute("member",member);
        return "/admin/plAdminMemberEdit";
    }
    //회원 상세페이지 수정
    @PostMapping("/member/edit/{id}")
    public String AdminEditMember(@PathVariable("id") Long id, @ModelAttribute MemberDTO memberDTO) throws Exception{
        System.out.println("memberDTO="+memberDTO);
        memberService.AdminEditMember(memberDTO,id);
        return "redirect:/member/edit/"+id;
    }

    //회원 상세페이지 멤버삭제
    @GetMapping("/member/delete")
    public String memberDelete(@RequestParam("id")Long id) throws Exception {
        System.out.println("id="+id);
        memberService.deleteMember(id);
        return "redirect:/member/list";
    }

    //체크박스 회원 삭제
    @PostMapping("/member/delete")
    public String deleteMembers(@RequestParam("selectedMembers")List<Long> ids) throws Exception {
        System.out.println("id="+ids);
        memberService.deleteMembers(ids);
        return "redirect:/member/list";
    }


}
