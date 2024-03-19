package com.vw.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity(name = "costDetails")
public class CostDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int costId;

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


}
