package com.example.todo.service;

import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTasks() {
        Task task1 = new Task(1L, "Task 1", "Description 1", false);
        Task task2 = new Task(2L, "Task 2", "Description 2", true);
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertTrue(result.get(1).isCompleted());

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskById() {
        Task task = new Task(1L, "Task 1", "Description 1", false);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> taskOptional = taskService.getTaskById(1L);
        assertTrue(taskOptional.isPresent());

        Task result = taskOptional.get();

        assertEquals("Task 1", result.getTitle());
        assertFalse(result.isCompleted());

        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTaskByIdNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.getTaskById(1L);

        assertTrue(result.isEmpty());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void createTask() {
        Task taskToCreate = new Task(null, "New Task", "New Description", false);
        Task createdTask = new Task(1L, "New Task", "New Description", false);

        when(taskRepository.save(any(Task.class))).thenReturn(createdTask);

        Task result = taskService.createTask(taskToCreate);

        assertEquals(1L, result.getId());
        assertEquals("New Task", result.getTitle());
        assertFalse(result.isCompleted());

        verify(taskRepository, times(1)).save(taskToCreate);
    }

    @Test
    void updateTask() {
        Task existingTask = new Task(1L, "Task 1", "Description 1", false);
        Task updatedTask = new Task(1L, "Updated Task", "Updated Description", true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            assertEquals(updatedTask.getTitle(), savedTask.getTitle());
            assertEquals(updatedTask.getDescription(), savedTask.getDescription());
            assertTrue(savedTask.isCompleted());
            return savedTask;
        });

        Optional<Task> resultOptional = taskService.updateTask(1L, updatedTask);

        assertTrue(resultOptional.isPresent());
        Task result = resultOptional.get();

        assertEquals("Updated Task", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertTrue(result.isCompleted());

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(existingTask);
    }


    @Test
    void updateTaskNotFound() {
        Long taskId = 1L;
        Task updatedTask = new Task();

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.updateTask(taskId, updatedTask);

        assertTrue(result.isEmpty());

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, never()).save(any());
    }

    @Test
    void deleteTask() {
        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void getCompletedTasks() {
        Task completedTask1 = new Task(1L, "Completed Task 1", "Description 1", true);
        Task completedTask2 = new Task(2L, "Completed Task 2", "Description 2", true);
        List<Task> completedTasks = Arrays.asList(completedTask1, completedTask2);

        when(taskRepository.findByCompleted(true)).thenReturn(completedTasks);

        List<Task> result = taskService.getCompletedTasks();

        assertEquals(2, result.size());
        assertEquals("Completed Task 1", result.get(0).getTitle());
        assertTrue(result.get(1).isCompleted());

        verify(taskRepository, times(1)).findByCompleted(true);
    }

    @Test
    void testTaskEquals() {
        Task task1 = new Task(1L, "Task 1", "Description 1", false);
        Task task2 = new Task(1L, "Task 1", "Description 1", false);

        assertEquals(task1, task2);
        assertEquals(task1.hashCode(), task2.hashCode());
    }
}
