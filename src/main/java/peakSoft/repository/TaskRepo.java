package peakSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peakSoft.entity.Task;

@Repository
public interface TaskRepo extends JpaRepository<Task,Long> {

}
