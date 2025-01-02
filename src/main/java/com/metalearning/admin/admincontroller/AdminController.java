package com.metalearning.admin.admincontroller;

import com.metalearning.KDT.KDTdto.KDTCourseDTO;
import com.metalearning.KDT.KDTdto.KDTSessionDTO;
import com.metalearning.KDT.KDTservice.KdtService;
import com.metalearning.user.userservice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j

public class AdminController {

    private final KdtService kdtService;
    private final UserService userService;

    //국비과정 등록하기
    @GetMapping("/admin/KDT/course")
    public String getkdtcourse(){
        return "/admin/KDT/course";
    }

    //국비과정 등록하기
    @PostMapping("/admin/KDT/course")
    public String postkdtcourse(KDTCourseDTO kdtCourseDTO, Model model){


        log.info("데이터 오는지 확인해보자===============================-{}",kdtCourseDTO);


        int result = kdtService.kdtcoursesave(kdtCourseDTO);

        switch (result) {
            case 1: // 성공
                model.addAttribute("msg", "국비과정이 등록이 완료되었습니다!");
                model.addAttribute("loc", "/admin/KDT/session");  // 회차 입력하는 곳으로 이동
                break;
            case 2: // 이미 있음
                model.addAttribute("msg", "이미 존재하는 국비과정입니다!");
                model.addAttribute("loc", "/admin/KDT/course");  // 다시 입력 페이지로
                break;
            default: // 실패
                model.addAttribute("msg", "국비과정 등록을 실패했습니다!");
                model.addAttribute("loc", "/admin/KDT/course");  // 다시 입력 페이지로
                break;
        }

        return "/utility/message";
    }


    // 국비과정 수정하기
    @GetMapping("/admin/KDT/course/update/{courseId}")
    public String getkdtcourseupdate(@PathVariable Long courseId, Model model) {
        // Service에서 과정 정보 가져오기
        KDTCourseDTO kdtcourse = kdtService.kdtcourseupdate(courseId);

        // 모델에 DTO 객체를 담아서 뷰로 전달
        model.addAttribute("kdtcourse", kdtcourse);

        return "/admin/KDT/courseupdate"; // courseupdate.html로 이동
    }


    @PostMapping("/admin/KDT/course/update/{courseId}")
    public String updateCourse(@PathVariable Long courseId, @ModelAttribute KDTCourseDTO kdtcourse, Model model) {
        // 수정된 정보를 서비스로 전달하여 업데이트 처리
        boolean update = kdtService.updateCourse(courseId, kdtcourse);

        if (update) {
            // 수정 성공 시
            model.addAttribute("msg", "국비과정이 수정되었습니다.");
            model.addAttribute("loc", "/");  // 목록으로 리디렉션
        } else {
            // 수정 실패 시
            model.addAttribute("msg", "국비과정 수정이 실패했습니다.");
            model.addAttribute("loc", "/admin/KDT/course/update/" + courseId);  // 수정 페이지로 리디렉션
        }

        // 결과 메시지 페이지로 이동
        return "/utility/message";
    }


    //회차등록하기
    @GetMapping("/admin/KDT/session")
    public String getkdtsession(Model model) {
        List<KDTCourseDTO> courseall = kdtService.courseall();
        model.addAttribute("courseall", courseall);  // 데이터를 모델에 추가하여 뷰로 전달
        return "/admin/KDT/session";  // 해당 뷰로 이동

    }

    //회차등록하기
    @PostMapping("/admin/KDT/session")
    public String postkdtsession(KDTSessionDTO kdtSessionDto, Model model){


        log.info("데이터 오는지 확인해보자===============================-{}",kdtSessionDto);

        int result = kdtService.kdtsessionsave(kdtSessionDto);

        switch (result) {
            case 1: // 성공
                model.addAttribute("msg", "국비과정 회차 등록이 완료되었습니다!");
                model.addAttribute("loc", "/");  // 홈으로 리디렉션
                break;
            case 2: // 이미 있음
                model.addAttribute("msg", "이미 존재하는 회차과정입니다!");
                model.addAttribute("loc", "/admin/KDT/session");  // 다시 입력 페이지로
                break;
            default: // 실패
                model.addAttribute("msg", "국비과정회차 등록을 실패했습니다!");
                model.addAttribute("loc", "/admin/KDT/session");  // 다시 입력 페이지로
                break;
        }

        return "/utility/message";
    }

    // 관리자 마이페이지 가는 곳
    @GetMapping("/admin/mypage")
    public String adminmypage(){
        return "/users/detail";
    }






}