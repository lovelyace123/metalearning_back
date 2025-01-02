package com.metalearning.main.maincontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(){
        return "/home/home";
    }

    @GetMapping("/main/KDT-curriculum")
    public String KDTcurriculum(){
        return "/main/KDT-curriculum";
    }


//    @GetMapping("/main/KDT-curriculum")
//    public String
}
