package com.vw.service.impl;

import com.vw.controller.AcceptanceController;
import com.vw.repository.AcceptanceRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.vw.model.ProjectDetails;
import com.vw.service.AcceptanceService;
import com.vw.utility.Utilities;

import java.io.FileOutputStream;
import java.io.IOException;


@Service
public class AcceptanceServiceImpl implements AcceptanceService {
    Logger log = LoggerFactory.getLogger(AcceptanceServiceImpl.class);
    @Autowired
    private AcceptanceRepository repository;

    @Override
    public ResponseEntity<String> createProject(ProjectDetails projectDetails) {
        log.info("inside the createProject() method ");
        String projectId = projectDetails.getProjectName().concat(projectDetails.getAgrmntNumber());
        ProjectDetails projectData = repository.findByProjectId(projectId);
        FileOutputStream fileReport = null;
        if (projectData == null) {
            projectDetails.setProjectId(projectId);
            /*calCurrentMonthBdgt(projectDetails);
            calMiscBdgt(projectDetails);
            calTotals(projectDetails);*/
            try {
                Utilities.generateReport(projectDetails);
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
        } else {


        }
        repository.save(projectDetails);

        log.debug("Record has been Stored ");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

/*    private void calMiscBdgt(ProjectDetails projectDetails) {
        projectDetails.setMiscMonthlyBdgt(projectDetails.getMiscPricing());
        projectDetails.setMiscRemainBdgt(projectDetails.getMiscCost() - projectDetails.getMiscPricing());
    }

    private void calCurrentMonthBdgt(ProjectDetails projectDetails) {
        double totalMonthlyCost = projectDetails.getPricing() * projectDetails.getMember();
        projectDetails.setSrvcMonthlyCost(totalMonthlyCost);
        projectDetails.setSrvcRemainBdgt(projectDetails.getSrvcCost() - totalMonthlyCost);
    }

    private static void calTotals(ProjectDetails proData) {
        proData.setTotalCost(proData.getSrvcCost() + proData.getMiscCost());
        proData.setTotalMonthlyBdgt(proData.getSrvcMonthlyCost() + proData.getMiscMonthlyBdgt());
        proData.setTotalRemainBdgt(proData.getSrvcRemainBdgt() + proData.getMiscRemainBdgt());
    }*/

}
