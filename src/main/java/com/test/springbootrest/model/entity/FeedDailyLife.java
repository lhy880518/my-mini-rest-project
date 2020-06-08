package com.test.springbootrest.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class FeedDailyLife {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    public FeedDailyLife(String name) {
        this.name = name;
    }
}