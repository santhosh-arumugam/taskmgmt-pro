package com.development.taskmgmt_pro.repository;

import com.development.taskmgmt_pro.model.Project;
import com.development.taskmgmt_pro.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllProjectByStatus(String projectStatus);
    List<Task> findByProjectId(Long projectId);

    @Query("SELECT p FROM Project p WHERE p.projectName LIKE %:keyword%")
    List<Project> searchProjectsByProjectName(@Param("keyword") String projectName);

    //int save(Project project);
    //List<Project> findAll();
    //Optional<Project> findById(Long projectId);
    //void deleteById(Long projectId);
    //void deleteByProjectName(String projectName);

}
