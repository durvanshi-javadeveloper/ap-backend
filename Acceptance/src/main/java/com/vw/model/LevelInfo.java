package com.vw.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LevelInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int levelId;
    private int level;
    private int member;
    private double price;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private ProjectDetails projectDetails;
}
