package com.example.demo;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class MemberTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void join() throws Exception{
        //Given : Member 빌더로 멤버 객체 생성 후
        Member member = new Member();
        member.setName("winter");
        member.setId("elsa");
        member.setOrg("sm");
        member.setActive(true);

        // When : 멤버가 가입되면 (MemberService, 스프링컨텍스트가 사용된걸 확인)
        Member storedMember = memberService.addUser(member);

        // Then : 그 멤버를 다시 찾을때  둘이 이름이 같아야 한다.
        Member foundMember = memberRepository.findById(storedMember.getSeq()).get();
        assertEquals(member.getName(), foundMember.getName());
    }



}
