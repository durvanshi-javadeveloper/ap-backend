package com.vw.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "levelInfo")
@Data
public class LevelInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int levelId;
    private int level;
    private int member;
    private double price;

    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectDetails projectDetails;*/
}
