package com.metalearning;

import com.metalearning.user.userdto.UserSignUpDTO;
import com.metalearning.user.userservice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class test2 {
    private final UserService userService;

    //어드민 대시보드 보려고 테스트한거임 지워야함
    @GetMapping("/admin/test")
    public String admintest(){
        return "/admin/KDT/admindashboard";
    }

    //테스트임
    @GetMapping("/admin/test1")
    public String admintest1() {
        return "/view/xe";
    }

    @GetMapping("/admin/test2")
    public String admintest2(Model model) {
        // 모든 사용자 정보를 가져오는 서비스 메서드 호출
        List<UserSignUpDTO> useall = userService.userall();

        // 모델에 사용자 리스트를 추가하여 뷰로 전달
        model.addAttribute("users", useall);

        // 뷰 이름을 반환 (view/users.html로 렌더링)
        return "/view/users";
    }


}
