package com.legday.backboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder // 객체 생성 간략화
@NoArgsConstructor // JPA 엔티티는 기본생성자 필요
@AllArgsConstructor // 생성자(모든필드)
@Entity // Table
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long bno;

    @Column(name = "title", length = 250, nullable = false)
    private String title;

    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    @ColumnDefault(value = "0")
    private Integer hit; // 조회수 컬럼

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate // 수정일자
    @Column(name = "modifyDate")
    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Reply> replies;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @Override
    public String toString() {
        return "Board{" +
                "modifyDate=" + modifyDate +
                ", createDate=" + createDate +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", bno=" + bno +
                '}';
    }
}
