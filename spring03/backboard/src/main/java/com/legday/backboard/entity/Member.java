package com.legday.backboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long mid;

    @Column(unique = true, length = 100)
    private String username;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 12)
    private MemberRole role;

    @CreatedDate
    @Column(name = "regDate", updatable = false)
    private LocalDateTime createDate;

    @Override
    public String toString() {
        return "Member{" +
                "mid=" + mid +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
