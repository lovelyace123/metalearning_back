package com.metalearning.admin.admincontroller;

import com.metalearning.KDT.KDTdto.KDTCourseDTO;
import com.metalearning.KDT.KDTservice.KdtService;
import com.metalearning.utility.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/KDT")
@Slf4j
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
            List<KDTCourseDTO> courseall = kdtService.courseall();

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
            // 서비스에서 삭제 처리 (과정 삭제)
            boolean isDeleted = kdtService.deleteCourse(courseId);

            if (isDeleted) {
                // 삭제가 성공했을 때
                ResponseMessage response = new ResponseMessage("success", "삭제 성공했습니다.");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                // 삭제가 실패했을 때 (과정이 존재하지 않거나, 세션이 존재하면)
                // 먼저 세션 존재 여부를 확인하고 적절한 메시지 반환
                boolean hasSessions = kdtService.hasSessions(courseId);

                if (hasSessions) {
                    ResponseMessage response = new ResponseMessage("error", "회차가 존재하여 삭제할 수 없습니다.");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response); // HTTP 403 Forbidden
                } else {
                    ResponseMessage response = new ResponseMessage("error", "삭제할 과정을 찾을 수 없습니다.");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // HTTP 404 Not Found
                }
            }
        } catch (Exception e) {
            // 오류가 발생했을 때
            ResponseMessage response = new ResponseMessage("error", "삭제 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // HTTP 500 Internal Server Error
        }
    }




}
