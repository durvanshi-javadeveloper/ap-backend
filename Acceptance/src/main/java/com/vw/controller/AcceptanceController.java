package com.vw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vw.model.ProjectDetails;
import com.vw.service.AcceptanceService;
import static com.vw.utility.Constants.ACCEPTANCE;
import static com.vw.utility.Constants.CREATE_PROJECT;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(ACCEPTANCE)
public class AcceptanceController {
    Logger log = LoggerFactory.getLogger(AcceptanceController.class);
    @Autowired
    private AcceptanceService acceptanceService;

    @PostMapping(value = CREATE_PROJECT)
    public ResponseEntity<String> createProject(@RequestBody ProjectDetails projectDetails){
        log.info("In Controller......");
        ResponseEntity<String> projectResponse = acceptanceService.createProject(projectDetails);

        return projectResponse;
        //  return new ResponseEntity<>("Project Created "+projectDetails.getProjectId(),HttpStatus.CREATED);
    }

//    localhost:8080/acceptance/create-project

}
