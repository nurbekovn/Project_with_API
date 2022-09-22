package com.services;

import com.dto.requests.TaskRequest;
import com.dto.response.TaskResponse;
import com.entities.Lesson;
import com.entities.Task;
import com.exceptions.NotFoundException;
import com.repository.LessonRepository;
import com.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepo;
    private final LessonRepository lessonRepo;

    public TaskResponse saveTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTaskName(taskRequest.getTaskName());
        task.setTaskText(taskRequest.getTaskText());
        task.setDeadline(taskRequest.getDeadline());
//        task.setDeadline(Period.between(taskRequest.getDeadline(),LocalDate.now()).getYears());
        Lesson lesson = lessonRepo.findById(taskRequest.getLessonId()).orElseThrow(
                () -> new NotFoundException(String.format("Lesson with =%s id not found", taskRequest.getLessonId())));
        task.setLesson(lesson);
        lesson.addTask(task);
        Task task1 = taskRepo.save(task);
        return response(task1);
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Task with =%s id not founded", id)));
        return response(task);
    }


    public TaskResponse updateTaskById(Long id, TaskRequest taskRequest) {
        Task task = taskRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Task with =%s id not founded", id)));
        Task task1 = update(task, taskRequest);
        return response(task1);
    }

    private Task update(Task task, TaskRequest taskRequest) {
        task.setTaskName(taskRequest.getTaskName());
        task.setTaskText(taskRequest.getTaskText());
        task.setDeadline(taskRequest.getDeadline());
        Lesson lesson = lessonRepo.findById(taskRequest.getLessonId()).orElseThrow(
                () -> new NotFoundException(String.format("Lesson with =%s id not founded", taskRequest.getLessonId())));
        task.setLesson(lesson);
        lesson.addTask(task);
        return taskRepo.save(task);
    }

    public TaskResponse deleteTaskById(Long id) {
        Task task = taskRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Task with =%s id not found", id)));
        task.setLesson(null);
        taskRepo.delete(task);
        return response(task);
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepo.getAllTasks();
    }

    public TaskResponse response(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTaskName(task.getTaskName());
        response.setTaskText(task.getTaskText());
        response.setDeadline(task.getDeadline());
        return response;
    }
}
