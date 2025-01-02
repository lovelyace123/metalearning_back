package com.metalearning.utility;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

    // 접근이 거부된 후 리디렉션을 처리하는 핸들러
    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "redirect:/public/accessDenied";  // 리디렉션: "/public"으로 이동
    }

    // "/public" 페이지를 처리하는 핸들러
    @GetMapping("/public/accessDenied")
    public String accessDeniedView() {
        return "/utility/accessDenied";  // "utility/accessDenied" 뷰를 반환
    }

}
