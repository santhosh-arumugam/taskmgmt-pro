package com.development.taskmgmt_pro.repository;

import com.development.taskmgmt_pro.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAll(Pageable pageable);

    Optional<Task> findByTitle(String title);
    @Query(value = "SELECT * FROM Task WHERE status LIKE %:keyword%", nativeQuery = true)
    List<Task> searchTasksByStatus(@Param("keyword") String status);

    @Query(value = "SELECT COUNT(*) FROM Task T JOIN Project P WHERE T.projectId = P.projectId AND P.projectId = :projectId", nativeQuery = true)
    int countTasksByProjectId(@Param("projectId") Long projectId);

    @Query(value = "SELECT COUNT(*) FROM Task T JOIN Project P WHERE T.projectId = P.projectId AND P.projectId = :projectId AND T.status LIKE %:status%", nativeQuery = true)
    int countTasksByProjectId(@Param("projectId") Long projectId, @Param("status") String status);

}
