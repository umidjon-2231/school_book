package com.project.school_book.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(nullable = false)
    private String name, type;
    @Column(nullable = false)
    private Long size;
    private byte[] bytes;
}
