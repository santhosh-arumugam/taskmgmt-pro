package com.development.taskmgmt_pro.repository;

import com.development.taskmgmt_pro.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProjectRepositoryTest {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectRepositoryTest(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Test
    void testSaveProject() {

        //Given
        Project project = new Project();
        project.setProjectName("External Channels Integration");

        //When
        Project savedProject = projectRepository.save(project);

        //Then
        assertNotNull(savedProject.getProjectId(), "Project should be generated");
        assertEquals("External Channels Integration", savedProject.getProjectName(), "Project name should be inserted");

        //Verify in database
        Optional<Project> found = projectRepository.findById(savedProject.getProjectId());
        assertTrue(found.isPresent(), "Project Id should be fetched from database");
        assertEquals("External Channels Integration", found.get().getProjectName());
    }
}
