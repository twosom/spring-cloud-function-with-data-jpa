package com.icloud.functionpractice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Memo(String description) {
        this.description = description;
    }

    public Memo() {
    }
}
