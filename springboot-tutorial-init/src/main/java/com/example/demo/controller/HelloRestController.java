package com.example.demo.controller;

import com.example.demo.entity.Hello;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
@RestController
@RequiredArgsConstructor    //@Autowired와 constructor 대신
public class HelloRestController {
    //@Autowired ->Field Injection

    final MemberService memberService;  //이 방법 권장

/*
    @Autowired
    public HelloRestController(MemberService memberService) {
        this.memberService = memberService;
    }
*/

/*
    @Autowired
    private void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
*/

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(String name) {
//        MemberService service = new MemberService();
//        return service.greet(name);
        // 이렇게 하는게 B 따라서 Bean 사용.
        //return String.format("Hello, %s", name);
        return memberService.greet(name);
    }

    @GetMapping("hello-map")
    public Map<String,Object> helloMap(String name) {
        Map<String,Object> m = new HashMap<>();   // 키,value
        m.put("name", name);
        return m;
    }

    @GetMapping("hello-object")
    public Hello helloObject(String name) {
        Hello h = new Hello(name);
        return h;
    }


}
