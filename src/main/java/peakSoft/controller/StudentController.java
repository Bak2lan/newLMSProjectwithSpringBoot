package peakSoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peakSoft.entity.Student;
import peakSoft.enums.StudyFormat;
import peakSoft.service.StudentService;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor()
public class StudentController {
    private final StudentService studentService;


    @GetMapping("{companyId}/{courseId}/{groupId}/create")
    public String createStudent(@PathVariable Long companyId,
                                @PathVariable Long courseId,
                                @PathVariable Long groupId,
                                Model model){
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("groupId",groupId);
        model.addAttribute("studyFormat",StudyFormat.values());
        model.addAttribute("newStudent",new Student());
        return "student/newStudent";
    }

    @PostMapping("{companyId}/{courseId}/{groupId}/save")
    public String saveStudent(@PathVariable Long companyId,
                              @PathVariable Long courseId,
                              @PathVariable Long groupId,
                              Model model,
                              @ModelAttribute("newStudent")Student student){
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("groupId",groupId);
        studentService.saveStudentToGroup(groupId,student);
        return "redirect:/groups/"+companyId+"/"+courseId+"/"+groupId+"/get";

    }
    @GetMapping("{companyId}/{courseId}/{groupId}/{id}/form")
    public String formUpdate(@PathVariable Long companyId,
                             @PathVariable Long courseId,
                             @PathVariable Long groupId,
                             @PathVariable Long id,
                             Model model){
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("groupId",groupId);
        model.addAttribute("studyFormat",StudyFormat.values());
        model.addAttribute("updatedStudent",studentService.getByIdStudent(id));

        return "student/updateStudent";

    }

    @PostMapping("{companyId}/{courseId}/{groupId}/{id}/update")
    public String updateStudent(@PathVariable Long companyId,
                                @PathVariable Long courseId,
                                @PathVariable Long groupId,
                                @PathVariable Long id,
                                @ModelAttribute("updatedStudent")Student student,
                                Model model){
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("groupId",groupId);
        studentService.updateStudent(id,student);
        return "redirect:/groups/"+companyId+"/"+courseId+"/"+groupId+"/get";

    }

    @PostMapping("{companyId}/{courseId}/{groupId}/{id}/delete")
    public String deleteStudent(@PathVariable Long companyId,
                                @PathVariable Long courseId,
                                @PathVariable Long groupId,
                                @PathVariable Long id,
                                Model model){
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("groupId",groupId);
        studentService.deleteStudent(id);
        return "redirect:/groups/"+companyId+"/"+courseId+"/"+groupId+"/get";

    }


}
