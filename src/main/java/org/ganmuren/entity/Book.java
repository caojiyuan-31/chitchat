package org.ganmuren.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//name为表名
@Data
@Entity(name = "book")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "book_name", nullable = false)
    private String name;

    @Column(name = "book_author", nullable = false)
    private String author;

    @Column(name = "book_price")
    private Float price;

    private Date date;
}
