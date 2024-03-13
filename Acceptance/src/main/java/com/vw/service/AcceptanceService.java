package com.vw.service;

import org.springframework.http.ResponseEntity;
import com.vw.model.ProjectDetails;


public interface AcceptanceService {

    public ResponseEntity<String> createProject(ProjectDetails projectDetails);
    public ProjectDetails getProject(String agrmntNumber);


}
