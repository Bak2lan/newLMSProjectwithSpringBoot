package peakSoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peakSoft.entity.Lesson;
import peakSoft.entity.Task;
import peakSoft.exception.MyException;
import peakSoft.repository.LessonRepo;
import peakSoft.repository.TaskRepo;
import peakSoft.service.TaskService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final LessonRepo lessonRepo;

    @Override
    public void saveTaskLesson(Long lessonId, Task task) {
        try {
            Lesson lessonById = lessonRepo.getLessonById(lessonId);
            if (lessonById == null) {
                throw new MyException("not found lesson");
            } else {
                lessonById.getTasks().add(task);
                task.setLesson(lessonById);
                taskRepo.save(task);
            }
        }catch (MyException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepo.findAll();
    }

    @Override
    public void updateTask(Long id, Task newTask) {
        Task task = getTaskByID(id);
        task.setTaskName(newTask.getTaskName());
        task.setTaskText(newTask.getTaskText());
        task.setDeadLine(newTask.getDeadLine());
    }

    @Override
    public void deleteTask(Long id) {
        Task task = getTaskByID(id);
        taskRepo.deleteById(task.getId());
    }

    @Override
    public Task getTaskByID(Long id) {
        return taskRepo.findById(id).orElseThrow(()->new NoSuchElementException(String.format("Not found task with id %s",id)));
    }
}
