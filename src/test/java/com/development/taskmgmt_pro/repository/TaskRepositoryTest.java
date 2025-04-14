package com.development.taskmgmt_pro.repository;

import com.development.taskmgmt_pro.model.Project;
import com.development.taskmgmt_pro.model.Task;
import com.development.taskmgmt_pro.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTest {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskRepositoryTest(UserRepository userRepository, ProjectRepository projectRepository,TaskRepository taskRepository)
    {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Test
    void testSaveTask() {

        //Given User Model
        User user = new User();
        user.setUserName("Santhosh Kumar");
        user.setEmailId("SanthoshKumar@gmail.com");
        User savedUser = userRepository.save(user);

        //Given Project model
        Project project = new Project();
        project.setProjectName("External Channels integration");
        Project savedProject = projectRepository.save(project);

        //Given Task model
        Task task = new Task();
        task.setTitle("Create Channel entity");
        task.setPriority("High");
        task.setStatus("OPEN");
        task.setProject(savedProject);
        task.setUser(savedUser);

        //When
        Task savedTask = taskRepository.save(task);


        //Then
        assertNotNull(savedTask.getTaskId(), "Task Id should be generated and inserted");
        assertEquals("Create Channel entity", savedTask.getTitle(), "Should insert title");
        assertEquals("High", savedTask.getPriority(), "Should insert priority");
        assertEquals("OPEN", savedTask.getStatus(), "Should insert status");
        assertEquals(savedUser.getUserId(), savedTask.getUser().getUserId(), "User Id should be generated and inserted");
        assertEquals(savedProject.getProjectId(), savedTask.getProject().getProjectId(), "Project Id should be generated and inserted");

        //verify in database
        Optional<Task> found = taskRepository.findById(savedTask.getTaskId());
        assertTrue(found.isPresent(), "Should get taskId from database");
        assertEquals("Create Channel entity", found.get().getTitle() );
        assertEquals("High", found.get().getPriority());
        assertEquals("OPEN", found.get().getStatus());
        assertEquals(savedUser.getUserId(), found.get().getUser().getUserId());
        assertEquals(savedProject.getProjectId(), found.get().getProject().getProjectId());
    }
}
