package com.vw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProjectDetails{

    @Id
    private String projectId;

    private String department;

    private String subDeprtmt;

    private String projectName;

    private String date;

    private String fromDate;

    private String toDate;

    private String agrmntNumber;

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

    private double level;

    private double member;

    private double pricing;

    private double miscPricing;

}
