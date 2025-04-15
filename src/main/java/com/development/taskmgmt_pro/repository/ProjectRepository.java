package com.development.taskmgmt_pro.repository;

import com.development.taskmgmt_pro.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByProjectStatus(String projectStatus);

    @Query("SELECT p FROM Project p WHERE p.projectName LIKE %:keyword%")
    List<Project> searchProjectsByProjectName(@Param("keyword") String keyword);

}
