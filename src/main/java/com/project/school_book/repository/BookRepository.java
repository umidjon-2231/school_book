package com.project.school_book.repository;

import com.project.school_book.entity.Book;
import com.project.school_book.entity.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
//    @Query(nativeQuery = true,
//     value = "select * from book where name.")
//    List<Book> search(String key);

//    @Query(nativeQuery = true, value = "select * from book join groups g on g.id = book.group_id where name like concat('%', :key, '%') and g.class_number in (:classNumber) and language in (:lang)")
//    List<Book> search(String key, List<Integer> classNumber, List<Language> lang);

    List<Book> findAllByNameIsLikeAndGroup_ClassNumberInAndLanguageIn(
            String name, Collection<Integer> group_classNumber, Collection<Language> language);

    List<Book> findAllByNameIsLikeAndGroup_ClassNumberIn(String key, List<Integer> groupIds);

    List<Book> findAllByNameIsLikeAndLanguageIn(String key, List<Language> languages);

    List<Book> findAllByNameIsLike(String key);
}