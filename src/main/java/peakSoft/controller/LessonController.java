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
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;


    @GetMapping("{companyId}/{courseId}/create")
    public String createLesson(@PathVariable Long companyId,
                               @PathVariable Long courseId,
                               Model model){
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("newLesson",new Lesson());
        return "lesson/newLesson";

    }

    @PostMapping("{companyId}/{courseId}/save")
    public String saveLesson(@PathVariable Long companyId,
                             @PathVariable Long courseId,
                             @ModelAttribute("newLesson")Lesson lesson){
        lessonService.saveToCourse(courseId,lesson);
        return "redirect:/courses/"+companyId+"/"+courseId+"/get";

    }

    @GetMapping ("{companyId}/{courseId}/{id}/form")
    public String updateForm(@PathVariable Long companyId,
                             @PathVariable Long courseId,
                             @PathVariable Long id,
                             Model model){
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("updatedLesson",lessonService.getLessonById(id));
        return "lesson/updateLesson";

    }

    @PostMapping("{companyId}/{courseId}/{id}/update")
    public String updateLesson(@PathVariable Long companyId,
                               @PathVariable Long courseId,
                               @PathVariable Long id,
                               @ModelAttribute("updatedLesson")Lesson lesson){
        lessonService.updateLesson(id,lesson);
        return "redirect:/courses/"+companyId+"/"+courseId+"/get";
    }
    @PostMapping("{companyId}/{courseId}/{id}/delete")
    public String deleteLesson(@PathVariable Long companyId,
                               @PathVariable Long courseId,
                               @PathVariable Long id,
                               Model model){
        lessonService.deleteLesson(id);
        return "redirect:/courses/"+companyId+"/"+courseId+"/get";
    }
    @GetMapping("{companyId}/{courseId}/{id}/get")
    public String getLesson(@PathVariable Long companyId,
                            @PathVariable Long courseId,
                            @PathVariable Long id,
                            Model model){
        Lesson lesson = lessonService.getLessonById(id);
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("tasks",lesson.getTasks());
        return "lesson/getLesson";
    }

}
