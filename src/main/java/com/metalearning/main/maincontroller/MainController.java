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

    @GetMapping("/main/about-metalearning")
    public String aboutMetalearning(){
        return "/main/about-metalearning";
    }

    @GetMapping("/main/way-to-come")
    public String wayToCome(){
        return "/main/way-to-come";
    }

    @GetMapping("/main/KDT-info")
    public String kdtInfo(){
        return "/main/KDT-info";
    }

}
