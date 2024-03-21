package com.vw.service.impl;

import com.vw.repository.AcceptanceRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.binary.StringUtils;
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
import java.time.Month;


@Service
public class AcceptanceServiceImpl implements AcceptanceService {
    Logger log = LoggerFactory.getLogger(AcceptanceServiceImpl.class);
    @Autowired
    private AcceptanceRepository repository;
    ResponseEntity response = null;

    @Override
    public ResponseEntity<String> createProject(ProjectDetails projectDetails) {
        log.info("inside the createProject() method ");
        ProjectDetails existingRecord = repository.findByAgrmntNumberAndGeneratedDate(projectDetails.getAgrmntNumber(), projectDetails.getGeneratedDate());
        if (existingRecord != null) {
            Month month = Utilities.dateConvert(projectDetails.getToDate()).getMonth();
            LocalDate currentDate = Utilities.dateConvert(projectDetails.getToDate());
            if (!StringUtils.equals("JANUARY",month.toString())){
                ProjectDetails preMonthRecord = repository.findByAgrmntNumberAndGeneratedDate(projectDetails.getAgrmntNumber(),
                        Utilities.dateConvert(projectDetails.getGeneratedDate()).minusMonths(1).toString());
            Utilities.calculateCostPerMonth(projectDetails, preMonthRecord);
            for(LocalDate date =currentDate; date.getMonthValue()<=12;date.plusMonths(1)){


            }
            repository.save(projectDetails);

            try {
                Utilities.generateReport(projectDetails, Utilities.dateConvert(projectDetails.getFromDate()));

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
        }
        } else {
            LocalDate startDate = Utilities.dateConvert(projectDetails.getFromDate());
            LocalDate endDate = Utilities.dateConvert(projectDetails.getToDate());
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusMonths(1)) {
                System.out.println(date.getYear());
                if (date.isEqual(startDate)) {
                    ProjectDetails projectClone = new ProjectDetails();
                    try {
                        Utilities.prepairePersistanceData(projectDetails, projectClone);
                        Utilities.generateReport(projectDetails, date);
                    } catch (IOException | InvalidFormatException e) {
                        e.printStackTrace();
                    }
                    repository.save(projectClone);
                    log.debug("Record has been Stored ");
                    response = new ResponseEntity(HttpStatus.CREATED);
                } else {
                    Utilities.calculateCostPerMonth(projectDetails, null);
                    ProjectDetails projectClone = new ProjectDetails();
                    projectDetails.setGeneratedDate(Utilities.dateConvert(projectDetails.getGeneratedDate()).plusMonths(1).toString());
                    Utilities.prepairePersistanceData(projectDetails, projectClone);
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
        return response;
    }


    @Override
    public ProjectDetails getProject(String agrmntNumber, String generatedDate) {
        return repository.findByAgrmntNumberAndGeneratedDate(agrmntNumber, generatedDate);
    }

}
