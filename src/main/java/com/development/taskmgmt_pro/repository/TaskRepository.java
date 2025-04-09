package com.development.taskmgmt_pro.repository;

import com.development.taskmgmt_pro.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM Task WHERE status LIKE %:keyword%", nativeQuery = true)
    List<Task> searchTasksByStatus(@Param("keyword") String status);

    @Query(value = "SELECT COUNT(*) FROM Task T JOIN Project P WHERE T.projectId = P.projectId AND P.projectId = :projectId", nativeQuery = true)
    int countTasksByProjectId(@Param("projectId") Long projectId);

    @Query(value = "SELECT COUNT(*) FROM Task T JOIN Project P WHERE T.projectId = P.projectId AND P.projectId = :projectId AND T.status LIKE %:status%", nativeQuery = true)
    int countTasksByProjectId(@Param("projectId") Long projectId, @Param("status") String status);

    //int save(Task task);
    //Optional<Task> findById(Long taskId);
    //List<Task> findByTitle(String title);
    //void delete(Long taskId);
    //List<Task> findAllTaskByStatus(String status);

}
