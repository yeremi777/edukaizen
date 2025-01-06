package com.kokuu.edukaizen.entities.masters;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "master_languages")
@Getter
@Setter
@ToString
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Date created_at;

    @Column(name = "updated_at", insertable = false, updatable = true)
    private Date updated_at;

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = new Date();
    }

    public Language() {
    }

    public Language(String name) {
        this.name = name;
    }
}
