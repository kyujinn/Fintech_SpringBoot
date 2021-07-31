package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.logging.Logger;

@Controller
@Slf4j
public class HelloController {

    @GetMapping("hello-mvc")  // 주소창에서 바로 접근
    public String helloTemplate(String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @PostConstruct
    public void postConstruct() {
        log.info("I am already CONSTRUCTED");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("이번엔?");
        log.info("I will be destroyed");
    }

    // return : Hello yujin

//    @GetMapping("hello-string")
//    //@ResponseBody   // 전환이 되서 나감
//    public String helloString(String name) {
//        return String.format("Hello, %s", name);
//    }
//    // return : Hello, yujin
//
//    @GetMapping("hello-map")
//    @ResponseBody
//    public Map<String,Object> helloMap(String name) {
//        Map<String,Object> m = new HashMap<>();   // 키,value
//        m.put("name", name);
//        return m;
//    }
//    // return : {"name":"yujin"}  -> json 형태
//
//    @GetMapping("hello-object")
//    @ResponseBody
//    public Hello helloObject(String name) {
//        Hello h = new Hello(name);
//        return h;
//    }
    // return : {"name":"yujin"}  -> json 형태
}
