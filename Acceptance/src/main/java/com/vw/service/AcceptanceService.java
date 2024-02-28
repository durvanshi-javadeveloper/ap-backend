package com.vw.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import com.vw.model.ProjectDetails;

import java.io.IOException;

public interface AcceptanceService {

    public ResponseEntity<String> createProject(ProjectDetails projectDetails);


}
