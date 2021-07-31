package com.example.demo.controller;

import com.example.demo.empty.Hello;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {
    @GetMapping("hello-mvc")  // 주소창에서 바로 접근
    public String helloTemplate(String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }
    // return : Hello yujin

    @GetMapping("hello-string")
    @ResponseBody   // 전환이 되서 나감
    public String helloString(String name) {
        return String.format("Hello, %s", name);
    }
    // return : Hello, yujin

    @GetMapping("hello-map")
    @ResponseBody
    public Map<String,Object> helloMap(String name) {
        Map<String,Object> m = new HashMap<>();   // 키,value
        m.put("name", name);
        return m;
    }
    // return : {"name":"yujin"}  -> json 형태

    @GetMapping("hello-object")
    @ResponseBody
    public Hello helloObject(String name) {
        Hello h = new Hello(name);
        return h;
    }
    // return : {"name":"yujin"}  -> json 형태
}
