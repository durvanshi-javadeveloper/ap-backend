package com.vw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vw.model.ProjectDetails;

@Repository
public interface AcceptanceRepository extends JpaRepository<ProjectDetails, Integer> {

    ProjectDetails findByProjectId(Integer projectId);

    @Query(value = "SELECT * FROM project_details p WHERE p.agrmnt_Number=?1", nativeQuery = true)
    ProjectDetails finByAgrmntNumber(@Param(value = "agrmntNumber") String agrmntNumber);
}
