package peakSoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peakSoft.entity.Company;
import peakSoft.entity.Course;
import peakSoft.entity.Lesson;
import peakSoft.entity.Task;
import peakSoft.service.CompanyService;
import peakSoft.service.CourseService;
import peakSoft.service.LessonService;
import peakSoft.service.TaskService;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("{companyId}/{courseId}/{lessonId}/create")
    public String createTask(@PathVariable Long companyId,
                             @PathVariable Long courseId,
                             @PathVariable Long lessonId,
                             Model model){
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("lessonId",lessonId);
        model.addAttribute("newTask",new Task());
        return "task/newTask";

    }

    @PostMapping("{companyId}/{courseId}/{lessonId}/save")
    public String saveTask(@PathVariable Long companyId,
                           @PathVariable Long courseId,
                           @PathVariable Long lessonId,
                           @ModelAttribute("newTask")Task task){

        taskService.saveTaskLesson(lessonId,task);
        return "redirect:/lessons/"+companyId+"/"+courseId+"/"+lessonId+"/get";

    }

    @GetMapping("{companyId}/{courseId}/{lessonId}/{id}/form")
    public String formUpdate(@PathVariable Long companyId,
                             @PathVariable Long courseId,
                             @PathVariable Long lessonId,
                             @PathVariable Long id,
                             Model model){
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("lessonId",lessonId);
        model.addAttribute("updatedTask",taskService.getTaskByID(id));
        return "task/updateTask";
    }

    @PostMapping("{companyId}/{courseId}/{lessonId}/{id}/update")
    public String updateTask(@PathVariable Long companyId,
                             @PathVariable Long courseId,
                             @PathVariable Long lessonId,
                             @PathVariable Long id,
                             Model model,
                             @ModelAttribute("updatedTask")Task task){
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("lessonId",lessonId);
        taskService.updateTask(id,task);
        return "redirect:/lessons/"+companyId+"/"+courseId+"/"+lessonId+"/get";

    }

    @PostMapping("{companyId}/{courseId}/{lessonId}/{id}/delete")
    public String deleteTask(@PathVariable Long companyId,
                             @PathVariable Long courseId,
                             @PathVariable Long lessonId,
                             @PathVariable Long id,
                             Model model){
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("lessonId",lessonId);
        taskService.deleteTask(id);
        return "redirect:/lessons/"+companyId+"/"+courseId+"/"+lessonId+"/get";


    }




}
