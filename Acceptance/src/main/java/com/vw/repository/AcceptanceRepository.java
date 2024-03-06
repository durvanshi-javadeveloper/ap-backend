package com.vw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vw.model.ProjectDetails;

@Repository
public interface AcceptanceRepository extends JpaRepository<ProjectDetails, Integer> {


    ProjectDetails findByProjectId(String projectId);

    @Query("SELECT * FROM project_details where agrmntNumber =?")
    ProjectDetails finByAgrmntNumber(String agrmntNumber);
}
