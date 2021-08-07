package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
//    final MemberRepository memberRepository;
//
//    @GetMapping("/{key}")
//    public Member getMember(@PathVariable("key") Long key) {
//        return memberRepository.findById(key).orElse(null);
//    }

    @PostMapping("/save")
    public void saveMember(@RequestBody Member member){
        memberService.addUser(member);
    }

    final MemberService memberService;

    @GetMapping("/{key}")
    public Member getMember( @PathVariable("key") Long key, @RequestParam(required = false)String name )
    {
        //return memberRepository.findById(key).orElse(null);
        if(name != null){
            return memberService.findMember(key, name);
        } else {
            return memberService.findMember(key);
        }
    }

    @GetMapping("/api/count")
    //List<List<Map<String,Long>>>
    public List<Object> countByOrgGroup(@RequestParam Boolean isActive){
        return memberService.countOrgGroup(isActive);

    }
}
