package com.metalearning.KDT.KDTentity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "KDT_detail_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class KDTDetailFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KDT_detail_file_id")
    private Long kdtDetailFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KDT_detail_id", nullable = false)
    private KDTDetailEntity kdtDetailEntity; // KDT_detail 테이블과 연관

    @Column(name = "KDT_detail_file_name", nullable = false)
    private String kdtDetailFileName;   // 파일명

    @Column(name = "KDT_detail_file_UUID", nullable = false)
    private String kdtDetailFileUUID;   // UUID 파일명

    @Column(name = "KDT_detail_file_size", nullable = false)
    private Long kdtDetailFileSize;     // 파일 크기

    @Column(name = "KDT_detail_file_type", nullable = false)
    private String kdtDetailFileType;   // 파일 타입(확장자)

    @CreatedDate
    @Column(name = "KDT_detail_file_time", nullable = false)
    private LocalDateTime kdtDetailFileTime;    // 파일 업로드 시간

}
