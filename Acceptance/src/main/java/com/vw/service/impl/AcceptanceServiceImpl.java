package com.vw.service.impl;

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

import java.io.IOException;
import java.time.LocalDate;


@Service
public class AcceptanceServiceImpl implements AcceptanceService {
    Logger log = LoggerFactory.getLogger(AcceptanceServiceImpl.class);
    @Autowired
    private AcceptanceRepository repository;

    ResponseEntity response = null;

    @Override
    public ResponseEntity<String> createProject(ProjectDetails projectDetails) {
        log.info("inside the createProject() method ");
        if (projectDetails != null) {
            LocalDate startDate = Utilities.dateConvert(projectDetails.getFromDate());
            LocalDate endDate = Utilities.dateConvert(projectDetails.getToDate());
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusMonths(1)) {
                System.out.println(date.getYear());
                if (date.isEqual(startDate)) {
                    try {
                        Utilities.generateReport(projectDetails, date);
                    } catch (IOException | InvalidFormatException e) {
                        e.printStackTrace();
                    }
                    repository.save(projectDetails);
                    log.debug("Record has been Stored ");
                    response = new ResponseEntity(HttpStatus.CREATED);

                } else {
                    Utilities.calculateOtherMonths(projectDetails);
                    ProjectDetails projectClone = new ProjectDetails();
                    Utilities.prepairePersistanceData(projectDetails,projectClone);
                    try {
                        Utilities.generateReport(projectDetails, date);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InvalidFormatException e) {
                        e.printStackTrace();
                    }
                    repository.save(projectClone);
                    log.debug("Record has been Stored ");
                    response = new ResponseEntity(HttpStatus.CREATED);
                }
            }
        }
        /*ProjectDetails projectData = repository.findByAgrmntNumber(projectDetails.getAgrmntNumber());
        if (projectData == null) {
            try {
                Utilities.generateReport(projectDetails);
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
            repository.save(projectDetails);
            log.debug("Record has been Stored ");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            try {
                Utilities.generateReport(projectDetails);
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
            repository.save(projectDetails);
            return new ResponseEntity<>("Record Has been Updated ", HttpStatus.OK);
        }*/
        return response;
    }


    @Override
    public ProjectDetails getProject(String agrmntNumber, String generatedDate) {
        return repository.findByAgrmntNumberAndGeneratedDate(agrmntNumber, generatedDate);
    }

}
