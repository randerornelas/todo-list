package todoListBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import todoListBackend.model.TaskModel;
import todoListBackend.repository.TaskRepository;

import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public Iterable<TaskModel> getTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<TaskModel> getTaskById(@PathVariable int id) {
        return taskRepository.findById(id);
    }

    @PostMapping
    public TaskModel saveTask(TaskModel task) {
        taskRepository.save(task);

        return task;
    }

    @DeleteMapping
    public void deleteTask(@RequestParam (name = "id") int id) {
        taskRepository.deleteById(id);
    }
}
