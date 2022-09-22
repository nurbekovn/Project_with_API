package com.api;

import com.dto.requests.TaskRequest;
import com.dto.response.TaskResponse;
import com.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public TaskResponse saveTask(@RequestBody TaskRequest taskRequest) {
        return taskService.saveTask(taskRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTaskById(@PathVariable Long id,
                                       @RequestBody TaskRequest taskRequest) {
        return taskService.updateTaskById(id, taskRequest);
    }

    @DeleteMapping("/{id}")
    public TaskResponse deleteTaskById(@PathVariable Long id) {
        return taskService.deleteTaskById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('MANAGER')")
    public List<TaskResponse> getAllTask() {
        return taskService.getAllTasks();
    }
}
