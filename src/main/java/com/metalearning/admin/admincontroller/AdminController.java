package com.metalearning.admin.admincontroller;

import com.metalearning.KDT.KDTdto.KDTCourseDTO;
import com.metalearning.KDT.KDTdto.KDTSessionDTO;
import com.metalearning.KDT.KDTdto.KDTStaffDTO;
import com.metalearning.KDT.KDTservice.KdtService;
import com.metalearning.user.userdto.UserSignUpDTO;
import com.metalearning.user.userservice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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

    //수정 추가해서 푸시
    @PostMapping("/admin/KDT/session")
    public String postKdtSession(KDTSessionDTO kdtSessionDto, @RequestParam("files") MultipartFile file, Model model) throws IOException {

        // 파일 저장 경로 설정
        String uploadDir = "src/main/resources/static/images/course"; // 업로드 디렉토리
        Path uploadPath = Paths.get(uploadDir);

        // 디렉토리가 존재하지 않으면 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 업로드된 파일 처리
        String originalFileName = file.getOriginalFilename(); // 원본 파일 이름
        String uuidFileName = UUID.randomUUID().toString() + "_" + originalFileName; // UUID를 추가한 파일 이름
        Path filePath = uploadPath.resolve(uuidFileName); // 파일 저장 경로

        // 파일 저장
        Files.write(filePath, file.getBytes());

        // UUID 파일명을 kdtSessionThumbnail에 저장
        kdtSessionDto.setKdtSessionThumbnail(uuidFileName); // UUID 파일명만 설정 (실제 경로는 저장하지 않음)

        // 데이터 확인 (로그)
        log.info("데이터 오는지 확인해보자===============================-{}", kdtSessionDto);

        // 회차 저장 서비스 호출
        int result = kdtService.kdtsessionsave(kdtSessionDto);

        // 결과에 따라 메시지 처리
        switch (result) {
            case 1: // 성공
                model.addAttribute("msg", "국비과정 회차 등록이 완료되었습니다!");
                model.addAttribute("loc", "/");  // 홈으로 리디렉션
                break;
            case 2: // 이미 존재
                model.addAttribute("msg", "이미 존재하는 회차 과정입니다!");
                model.addAttribute("loc", "/admin/KDT/session");  // 다시 입력 페이지로
                break;
            default: // 실패
                model.addAttribute("msg", "국비과정 회차 등록을 실패했습니다!");
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


    //회차별 매니저 등록하기
    @GetMapping("/admin/KDT/{sessionId}/staff/manager")
    public String showManagerRegistrationPage(@PathVariable Long sessionId, Model model) {

        //등록이 안댄 매니저 정보만 가져옴
        List<UserSignUpDTO> usermanager = userService.usermanagerall(sessionId);

        // 세션 정보 가져오기
        KDTSessionDTO sessions = kdtService.getSessionsBySessId(sessionId);

        //이미 등록된 강사 명단 가
        List<UserSignUpDTO> registeredmanagers = userService.userRegisteredManager(sessionId);


        log.info("모든 사용자 정보 가져오는 거 ========================{}", usermanager);
        log.info("세션 정보 가져오는 거 ========================{}", sessions);

        // 모델에 사용자 정보와 세션 정보 추가
        model.addAttribute("usermanager", usermanager);
        model.addAttribute("sessions", sessions);
        model.addAttribute("registeredmanagers", registeredmanagers);


        return "/admin/KDT/staffmanger"; // 매니저 등록 페이지의 뷰 이름
    }


    @PostMapping("/admin/KDT/{sessionId}/staff/manager")
    public String registerManager(@PathVariable Long sessionId,
                                      @ModelAttribute KDTStaffDTO kdtStaffDTO) {

        // 강사 등록 처리
        int result = userService.instrsave(kdtStaffDTO);

        // 결과에 따라 다르게 처리
        switch (result) {
            case 1:
                break;
            case 2:
                break;
            case 0:
                break;
            default:
        }

        // 강사 목록 페이지로 리다이렉트
        return "redirect:/admin/KDT/{sessionId}/staff";
    }







    //회차별 강사 등록하기
    @GetMapping("/admin/KDT/{sessionId}/staff/instr")
    public String showinstrRegistrationPage(@PathVariable Long sessionId, Model model) {

        // 등록이 안댄 강사 정보만 가져옴
        List<UserSignUpDTO> userinstr = userService.userinstrall(sessionId);

        // 세션 정보 가져오기
        KDTSessionDTO sessions = kdtService.getSessionsBySessId(sessionId);

        //이미 등록된 강사 명단 가
        List<UserSignUpDTO> registeredInstructors = userService.userRegisteredInstructors(sessionId);

        log.info("모든 사용자 정보 가져오는 거 ========================{}", userinstr);
        log.info("세션 정보 가져오는 거 ========================{}", sessions);

        // 모델에 사용자 정보와 세션 정보 추가
        model.addAttribute("userinstr", userinstr);
        model.addAttribute("sessions", sessions);
        model.addAttribute("registeredInstructors", registeredInstructors);


        return "/admin/KDT/staffinstr"; // 매니저 등록 페이지의 뷰 이름
    }

    // 강사 등록 처리 메서드
    @PostMapping("/admin/KDT/{sessionId}/staff/instr")
    public String registerInstructors(@PathVariable Long sessionId,
                                      @ModelAttribute KDTStaffDTO kdtStaffDTO) {

        // 강사 등록 처리
        int result = userService.instrsave(kdtStaffDTO);

        // 결과에 따라 다르게 처리
        switch (result) {
            case 1:
                break;
            case 2:
                break;
            case 0:
                break;
            default:
        }

        // 강사 목록 페이지로 리다이렉트
        return "redirect:/admin/KDT/{sessionId}/staff";
    }



    //회차별 수강생 등록하기
    @GetMapping("/admin/KDT/{sessionId}/part")
    public String showin (@PathVariable Long sessionId, Model model) {

        return "/admin/KDT/part"; // 매니저 등록 페이지의 뷰 이름
    }







}