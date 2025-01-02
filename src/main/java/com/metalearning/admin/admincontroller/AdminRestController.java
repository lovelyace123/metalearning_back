package com.metalearning.admin.admincontroller;

import com.metalearning.KDT.KDTdto.KDTCourseDto;
import com.metalearning.KDT.KDTservice.KdtService;
import com.metalearning.utility.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/KDT")
public class AdminRestController {

    private final KdtService kdtService;

    //  과정선택하며 회치랑 과정명 가져와서 넣어주기
    @GetMapping("/session/getSessionNum")
    public ResponseEntity<Map<String, Object>> getSessionNum(@RequestParam Long courseId) {

        // 세션 번호와 과정명을 가져오는 서비스 호출
        Map<String, Object> sessionInfo = kdtService.getSessionNumAndCourseTitleByCourseId(courseId);

        // 세션 번호 증가
        int sessionNum = (int) sessionInfo.get("sessionNum");
        String courseTitle = (String) sessionInfo.get("courseTitle");
        int nextSessionNumber = sessionNum + 1;

        // 응답 데이터 준비
        Map<String, Object> response = new HashMap<>();
        response.put("sessionNum", nextSessionNumber); // 세션 번호
        response.put("courseTitle", courseTitle);     // 과정명

        // 응답 반환
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getCourseList() {
        try {
            List<KDTCourseDto> courseall = kdtService.courseall();

            // 회차 정보가 없으면 실패 메시지와 함께 OK 응답 반환
            if (courseall.isEmpty()) {
                ResponseMessage response = new ResponseMessage("failure", "회차 정보가 없습니다.");
                return ResponseEntity.status(HttpStatus.OK).body(response); // 200 OK 응답
            }

            // 회차 정보가 있으면 성공 메시지와 함께 회차 리스트 반환
            return ResponseEntity.status(HttpStatus.OK).body(courseall); // 200 OK 응답

        } catch (Exception e) {
            // 예외 발생 시 에러 메시지와 함께 OK 응답 반환
            ResponseMessage response = new ResponseMessage("error", "오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(response); // 200 OK 응답
        }
    }


    @DeleteMapping("/course/delete/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        try {
            boolean isDeleted = kdtService.deleteCourse(courseId); // 서비스에서 삭제 처리

            if (isDeleted) {
                ResponseMessage response = new ResponseMessage("success", "삭제 성공했습니다.");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                ResponseMessage response = new ResponseMessage("error", "삭제할 과정을 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage("error", "삭제 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
