package com.vw.service.impl;

import com.vw.model.LevelInfo;
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
import java.util.Set;


@Service
public class AcceptanceServiceImpl implements AcceptanceService {
    Logger log = LoggerFactory.getLogger(AcceptanceServiceImpl.class);
    @Autowired
    private AcceptanceRepository repository;

    @Override
    public ResponseEntity<String> createProject(ProjectDetails projectDetails) {
        log.info("inside the createProject() method ");

        ProjectDetails projectData = repository.findByProjectId(projectDetails.getProjectId());
        if (projectData == null) {
            try {
                Utilities.generateReport(projectDetails);
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
           // repository.save(projectDetails);
            log.debug("Record has been Stored ");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            try {
                Utilities.generateReport(projectDetails);
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
           // repository.save(projectDetails);
            return new ResponseEntity<>("Record Has been Updated ", HttpStatus.OK);
        }
    }

    @Override
    public ProjectDetails getProject(String agrmntNumber) {
        return repository.finByAgrmntNumber(agrmntNumber);
    }

}
