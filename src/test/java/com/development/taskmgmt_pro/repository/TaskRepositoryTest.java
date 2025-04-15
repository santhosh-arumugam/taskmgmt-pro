package com.development.taskmgmt_pro.repository;

import com.development.taskmgmt_pro.model.Project;
import com.development.taskmgmt_pro.model.Task;
import com.development.taskmgmt_pro.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTest {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskRepositoryTest(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Test
    void testSaveTask() {

        //Given Project Model
        Project project = new Project();
        project.setProjectName("Products Integration");
        Project savedProject = projectRepository.save(project);

        //Given User Model
        User user = new User();
        user.setUserName("Kumar");
        user.setEmailId("kumar@gmail.com");
        User savedUser = userRepository.save(user);

        //Given Task Model
        Task task = new Task();
        task.setTitle("Create Model for Channels");
        task.setProject(savedProject);
        task.setUser(savedUser);

        //When
        Task savedTask = taskRepository.save(task);

        //Then
        assertNotNull(savedTask.getTaskId(), "Task should be inserted");
        assertNotNull(savedTask.getProject().getProjectId(), "Project should be inserted");
        assertNotNull(savedTask.getUser().getUserId(), "User should be inserted");
        assertEquals("Create Model for Channels", savedTask.getTitle(), "Title should be inserted");
        assertEquals("Products Integration", savedTask.getProject().getProjectName(), "Project name should be inserted");
        assertEquals("Kumar", savedTask.getUser().getUserName(), "User name should be inserted");
        assertEquals("kumar@gmail.com", savedTask.getUser().getEmailId(), "User email ID should be inserted");

    }

}
