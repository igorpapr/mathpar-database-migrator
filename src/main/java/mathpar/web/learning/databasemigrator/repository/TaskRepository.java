package mathpar.web.learning.databasemigrator.repository;

import mathpar.web.learning.databasemigrator.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
