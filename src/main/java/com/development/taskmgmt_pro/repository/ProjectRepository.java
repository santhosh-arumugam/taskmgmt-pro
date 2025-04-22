package com.development.taskmgmt_pro.repository;

import com.development.taskmgmt_pro.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByProjectStatus(String projectStatus);

    Page<Project> findAll(Pageable pageable);

    Optional<Project> findByProjectName(String projectName);

    @Query("SELECT p FROM Project p WHERE p.projectName LIKE %:keyword%")
    List<Project> searchProjectsByProjectName(@Param("keyword") String keyword);

}
