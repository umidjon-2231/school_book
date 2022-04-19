package com.project.school_book.entity;

import com.project.school_book.entity.enums.Language;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Group group;
    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(nullable = false)
    private Attachment file;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(nullable = false)
    private Attachment picture;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<String> authors;
}
