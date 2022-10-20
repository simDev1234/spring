package com.example.demo.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "contents")
    private String contents;

    @Column(name = "likes")
    @Min(value = 0)
    private long likes;

    @Column(name = "views")
    @Min(value = 0)
    private long views;

    @Column
    private boolean deleted;

    @Column(name = "registeredAt")
    @CreatedDate
    private LocalDateTime regDate;

    @Column(name = "updatedAt")
    @LastModifiedDate
    private LocalDateTime editDate;

    private LocalDateTime deletedDate;

}
