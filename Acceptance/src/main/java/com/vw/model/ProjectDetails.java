package com.vw.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity(name = "projectDetails")
public class ProjectDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer projectId;

    private String agrmntNumber;

    private String brandName;

    private String department;

    private String subDeprtmt;

    private String projectName;

    private String generatedDate;

    private String fromDate;

    private String toDate;

    private String ordrNumber;

    private String respPersonal;

    private String srvcProvider;

    private String srvcReceiver;

    private String prjctDesc;

    private double srvcCost;

    private double srvcMonthlyCost;

    private double srvcRemainBdgt;

    private double miscCost;

    private double miscMonthlyBdgt;

    private double miscRemainBdgt;

    private double totalCost;

    private double totalMonthlyBdgt;

    private double totalRemainBdgt;

    private double miscPricing;

    private String mngrName;

    private String clientName;

    @OneToMany(targetEntity = LevelInfo.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "projectId")
    private Set<LevelInfo> levelInfo;

    /*@OneToOne(targetEntity = CostDetails.class,cascade = CascadeType.ALL)
    private CostDetails costDetails;*/

}
