package com.legday.backboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MainController {

    @GetMapping({"/","/hello"})
    public String getHello(){
        log.info("getHello() 실행");
        return "hello";
    }
}
