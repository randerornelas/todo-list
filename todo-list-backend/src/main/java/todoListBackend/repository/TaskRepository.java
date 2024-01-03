package todoListBackend.repository;

import todoListBackend.model.TaskModel;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskModel, Integer> {

}
