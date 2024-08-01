package com.spring.web.controller;

import com.spring.web.annotation.Controller;
import com.spring.web.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/hello")
    public String hello() {
        return "what can i say, mamba out";
    }
}
