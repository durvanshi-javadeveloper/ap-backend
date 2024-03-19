package com.vw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vw.model.ProjectDetails;

@Repository
public interface AcceptanceRepository extends JpaRepository<ProjectDetails, String> {

/*    ProjectDetails findByAgrmntNumber(String agrmntNumbery, String generatedDate);*/

    @Query(value = "SELECT * FROM project_details p WHERE p.agrmnt_Number=?1 AND p.generated_Date=?2", nativeQuery = true)
    ProjectDetails findByAgrmntNumberAndGeneratedDate(@Param(value = "agrmntNumber") String agrmntNumber,
                                                      @Param(value = "generatedDate") String generatedDate);
}
