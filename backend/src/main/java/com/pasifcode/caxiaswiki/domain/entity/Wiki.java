package com.pasifcode.caxiaswiki.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_wiki")
@EntityListeners(AuditingEntityListener.class)
public class Wiki {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "wiki_id")
    private String id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String tags;
    private String imageUrl;
    @CreatedDate
    private LocalDateTime createdDate;
    @OneToMany(mappedBy = "wiki")
    private Set<Image> images = new HashSet<>();
}