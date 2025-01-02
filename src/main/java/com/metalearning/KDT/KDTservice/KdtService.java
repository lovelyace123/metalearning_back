package com.metalearning.KDT.KDTservice;

import com.metalearning.KDT.KDTdto.KDTCourseDTO;
import com.metalearning.KDT.KDTdto.KDTSessionDTO;

import java.util.List;
import java.util.Map;

public interface KdtService {

    int kdtcoursesave(KDTCourseDTO kdtCourseDto);

    int kdtsessionsave(KDTSessionDTO kdtSessionDto);

    List<KDTCourseDTO> courseall();

    // courseId에 해당하는 세션 번호와 과정명을 반환
    Map<String, Object> getSessionNumAndCourseTitleByCourseId(Long courseId);

    //국비과정 프라이머리키로 찾는 메서드
    KDTCourseDTO kdtcourseupdate(Long courseId);

    //국비과정 수정하는 메서드
    boolean updateCourse(Long courseId, KDTCourseDTO kdtcourse);

    // 국비과정 삭제하는 메서드
    boolean deleteCourse(Long courseId);

    // 세션이 존재하는지 확인하는 메서드
    boolean hasSessions(Long courseId);

}
