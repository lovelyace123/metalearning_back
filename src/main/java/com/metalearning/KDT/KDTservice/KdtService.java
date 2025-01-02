package com.metalearning.KDT.KDTservice;

import com.metalearning.KDT.KDTdto.KDTCourseDto;
import com.metalearning.KDT.KDTdto.KDTSessionDto;

import java.util.List;
import java.util.Map;

public interface KdtService {

    //국비과정 등록하는 메서드
    int kdtcoursesave(KDTCourseDto kdtCourseDto);

    //국비회차 등록하는 메서드
    int kdtsessionsave(KDTSessionDto kdtSessionDto);

    //국비과정 전체 찾는 메서드
    List<KDTCourseDto> courseall();

    // courseId에 해당하는 세션 번호와 과정명을 반환
    Map<String, Object> getSessionNumAndCourseTitleByCourseId(Long courseId);

    //국비과정 프라이머리키로 찾는 메서드
    KDTCourseDto kdtcourseupdate(Long courseId);

    //국비과정 수정하는 메서드
    boolean updateCourse(Long courseId, KDTCourseDto kdtcourse);

    //국비과정 삭제하는 메서드
    boolean deleteCourse(Long courseId); // 서비스에서 삭제 처리

}
