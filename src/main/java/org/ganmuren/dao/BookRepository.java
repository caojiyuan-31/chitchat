package org.ganmuren.dao;

import org.ganmuren.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//<实体模型，主键类型>
public interface BookRepository extends JpaRepository<Book, Integer> {
}
