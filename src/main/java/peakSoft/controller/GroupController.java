package peakSoft.controller;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peakSoft.entity.Company;
import peakSoft.entity.Course;
import peakSoft.entity.Group;
import peakSoft.entity.Student;
import peakSoft.service.CompanyService;
import peakSoft.service.CourseService;
import peakSoft.service.GroupService;
import peakSoft.service.StudentService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Controller
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;


    @GetMapping("{companyId}/{courseId}/newGroup")
    public String newGroup(@PathVariable Long companyId,
                           @PathVariable Long courseId,
                           Model model){
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("newGroup",new Group());
        return "group/newGroup";
    }
    @PostMapping("{companyId}/{courseId}/save")
    public String save(@ModelAttribute("newGroup")Group group,
                       @PathVariable Long companyId,
                       @PathVariable Long courseId,
                       Model model){
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        groupService.saveGroupToCourse(courseId,group);
        return "redirect:/courses/"+companyId+"/"+courseId+"/get";
    }
    @GetMapping("{companyId}/{courseId}/{id}/form")
    public String updateForm(@PathVariable Long companyId,
                             @PathVariable Long courseId,
                             @PathVariable Long id,
                             Model model){
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("updatedGroup",groupService.getById(id));
        return "group/updateForm";
    }

    @PostMapping("{companyId}/{courseId}/{id}/update")
    public String updateGroup(@ModelAttribute("updatedGroup")Group group,
                              @PathVariable Long companyId,
                              @PathVariable Long courseId,
                              @PathVariable Long id,
                              Model model){
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        groupService.updateGroup(id,group);
        return "redirect:/courses/"+companyId+"/"+courseId+"/get";
    }

    @PostMapping("{companyId}/{courseId}/{id}/delete")
    public String delete(@PathVariable Long companyId,
                         @PathVariable Long courseId,
                         @PathVariable Long id){

        groupService.deleteGroup(id);
        return "redirect:/courses/"+companyId+"/"+courseId+"/get";
    }

    @GetMapping("{companyId}/{courseId}/{id}/get")
    public String getGroup(@PathVariable Long companyId,
                           @PathVariable Long courseId,
                           @PathVariable Long id,
                           Model model){
        Group group = groupService.getById(id);
        model.addAttribute("companyId",companyId);
        model.addAttribute("courseId",courseId);
        model.addAttribute("students",group.getStudents());
        return "group/getGroup";
    }
}
