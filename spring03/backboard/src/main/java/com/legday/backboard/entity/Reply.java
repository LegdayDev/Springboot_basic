package com.legday.backboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

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

    @JoinColumn(name="BOARD_BNO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
