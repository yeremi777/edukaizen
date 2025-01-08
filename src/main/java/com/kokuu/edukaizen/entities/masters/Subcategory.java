package com.kokuu.edukaizen.entities.masters;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "master_subcategories")
@Getter
@Setter
@ToString
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "category_id", insertable = false, updatable = false)
    private int category_id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Date created_at;

    @Column(name = "updated_at", insertable = false, updatable = true)
    private Date updated_at;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    private Category category;

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = new Date();
    }

    public Subcategory() {
    }

    public Subcategory(String name, Category category) {
        this.name = name;
        this.category = category;
    }
}
