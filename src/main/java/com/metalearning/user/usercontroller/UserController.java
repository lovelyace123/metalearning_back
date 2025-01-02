package com.metalearning.user.usercontroller;

import com.metalearning.user.userdto.UserSignUpDTO;
import com.metalearning.user.userservice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String getsingup() {
        return "/users/signup";
    }

    @PostMapping("/signup")
    public String postSignUp(UserSignUpDTO userSignUpDTO, Model model) {
        // 회원가입 처리

        boolean userSave = userService.usersave(userSignUpDTO);

        if (userSave) {
            // 회원가입 성공 시
            model.addAttribute("msg", "회원가입이 완료되었습니다. 축하합니다!");
            model.addAttribute("loc", "/");  // 홈으로 리디렉션
        } else {
            // 회원가입 실패 시
            model.addAttribute("msg", "회원가입이 실패되었습니다.");
            model.addAttribute("loc", "/signup");  // 회원가입 페이지로 리디렉션
        }

        return "/utility/message";
    }

    @GetMapping("/login")
    public String getlogin() {
        return "/users/login";
    }




}
