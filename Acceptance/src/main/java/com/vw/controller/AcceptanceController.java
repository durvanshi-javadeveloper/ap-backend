package com.vw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vw.model.ProjectDetails;
import com.vw.service.AcceptanceService;

import static com.vw.utility.Constants.*;

@RestController
@CrossOrigin(origins = UI_URL)
@RequestMapping(ACCEPTANCE)
public class AcceptanceController {
    Logger log = LoggerFactory.getLogger(AcceptanceController.class);
    @Autowired
    private AcceptanceService acceptanceService;

    @PostMapping(value = CREATE_PROJECT)
    public ResponseEntity<String> createProject(@RequestBody ProjectDetails projectDetails){
        log.info("In Controller......");
        ResponseEntity<String> projectResponse = acceptanceService.createProject(projectDetails);
        log.info("Data has been stored and Report has been generated...");
        return projectResponse;
        //  return new ResponseEntity<>("Project Created "+projectDetails.getProjectId(),HttpStatus.CREATED);
    }

    public ResponseEntity<ProjectDetails> getProjectDetail(@PathVariable String agrmntNumber ){
        ProjectDetails proDetails =null;
        return ResponseEntity.ok().body(proDetails);
    }


}
