package com.legday.backboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long rno;

    private String content;

    @CreatedDate
    @Column(name = "createDate", updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate // 수정일자
    @Column(name = "modifyDate")
    private LocalDateTime modifyDate;

    @JoinColumn(name="BOARD_BNO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    @Override
    public String toString() {
        return "Reply{" +
                "modifyDate=" + modifyDate +
                ", createDate=" + createDate +
                ", content='" + content + '\'' +
                ", rno=" + rno +
                '}';
    }
}
