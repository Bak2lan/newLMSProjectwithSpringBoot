package peakSoft.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peakSoft.entity.Company;
import peakSoft.entity.Course;
import peakSoft.entity.Instructor;
import peakSoft.service.CompanyService;
import peakSoft.service.CourseService;
import peakSoft.service.InstructorService;


@Transactional
@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final CompanyService companyService;

    @GetMapping("{companyId}/allCourses")
    public String getAll(Model model,@PathVariable Long companyId){
        Company company=companyService.getById(companyId);
        model.addAttribute("companyId",companyId);
        model.addAttribute("allCourses",company.getCourses());
        return "course/companyCourses";
    }

    @GetMapping("{companyId}/newCourse")
    public String newCourse(Model model,@PathVariable Long companyId){
        model.addAttribute("newCourse",new Course());
        return "course/newCourse";
    }

    @PostMapping("{companyId}/save")
    public String save(@ModelAttribute("newCourse")Course course,@PathVariable Long companyId){
        Company company = companyService.getById(companyId);
        courseService.saveCourse(course);
        course.setCompany(company);
        return "redirect:/courses/"+companyId+"/allCourses";
    }

    @GetMapping("{companyId}/form/{id}")
    public String formCourse(@PathVariable Long companyId,@PathVariable Long id,Model model){
        model.addAttribute("updateCourse",courseService.getCourseById(id));
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",id);
        return "course/formCourse";

    }
    @PostMapping("{companyId}/update/{id}")
    public String update(@ModelAttribute("updateCourse")Course course,@PathVariable Long companyId,
                         @PathVariable Long id){
        courseService.updateCourse(id,course);

        return "redirect:/courses/"+companyId+"/allCourses";
    }

    @PostMapping("{companyId}/delete/{id}")
    public String delete(@PathVariable Long companyId,
                         @PathVariable Long id){
        courseService.delete(id);
        return "redirect:/courses/"+companyId+"/allCourses";
    }

    @GetMapping("{companyId}/{id}/get")
    public String getCourse(@PathVariable Long companyId,
                            @PathVariable Long id,
                            Model model){
        Company company = companyService.getById(companyId);
        Course course = courseService.getCourseById(id);


        model.addAttribute("companyId",companyId);
        model.addAttribute("companyInstructors",company.getInstructors());
        model.addAttribute("groups",course.getGroups());
        model.addAttribute("lessons",course.getLessons());
        model.addAttribute("instructor",course.getInstructor());

        return "course/getCourse";

    }
    @PostMapping("{companyId}/{id}/assign")
    public String assignInstructorToCourse(@PathVariable Long companyId,
                                           @RequestParam("instructorId")Long instructorId,
                                           @PathVariable Long id){
        courseService.assignInstructorToCourse(instructorId,id);
        return "redirect:/courses/"+companyId+"/"+id+"/get";
    }



}
