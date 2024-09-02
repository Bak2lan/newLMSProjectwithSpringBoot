package peakSoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peakSoft.entity.Company;
import peakSoft.entity.Instructor;
import peakSoft.enums.Specialization;
import peakSoft.service.CompanyService;
import peakSoft.service.InstructorService;


@Controller
@RequestMapping("/instructors")
@RequiredArgsConstructor
public class InstructorController {
    private final CompanyService companyService;
    private final InstructorService instructorService;

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("allInstructors",instructorService.getAllInstructors());
        return "company/mainPage";
    }
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("newInstructor", new Instructor());
        model.addAttribute("specializations",Specialization.values());
        return "instructor/newInstructor";


    }
    @GetMapping("/createForCompany/{companyId}")
    public String create1(Model model,@PathVariable Long companyId) {
        model.addAttribute("newInstructorr", new Instructor());
        model.addAttribute("specializations", Specialization.values());
        return "instructor/newInstructorForCompany";
    }

        @PostMapping("{companyId}/save")
    public String saveToCompanyId(@PathVariable Long companyId,
                                  @ModelAttribute("newInstructorr")Instructor instructor){
        instructorService.assignToCompany(companyId,instructor);
        return "redirect:/instructors/"+companyId+"/instructors";
    }

    @PostMapping("/save")
    public String saveInstructor(@ModelAttribute("newInstructor")Instructor instructor){
        instructorService.save(instructor);
        return "redirect:/companies";

    }
    @GetMapping("/{companyId}/instructors")
    public String companyInstructors(@PathVariable Long companyId,
                                     Model model){
        Company company=companyService.getById(companyId);
        model.addAttribute("companyId",companyId);
        model.addAttribute("instr",company.getInstructors());
        model.addAttribute("allInstructors",instructorService.getAllInstructors());
        return "instructor/companyInstructors";
    }
    @PostMapping("{companyId}/assign")
    public String assign (@PathVariable Long companyId,@RequestParam("instructorId") Long instructorId){
        companyService.assignInstructorToCompany(instructorId,companyId);

        return "redirect:/instructors/"+companyId+"/instructors";
    }
    @GetMapping("{id}/form")
    public String formUpdate(@PathVariable()Long id,Model model){
        model.addAttribute("updatedInstructor",instructorService.getInstructorById(id));
        model.addAttribute("spec",Specialization.values());
        return "instructor/formUpdate";
    }
    @PostMapping("{id}/update")
    public String updateInstructor(@PathVariable Long id,
                                   @ModelAttribute("updatedInstructor")Instructor instructor){
        instructorService.update(id,instructor);
        return "redirect:/companies";
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id){
        instructorService.deleteInstructor(id);
        return "redirect:/companies";
    }
    @GetMapping("{id}/get")
    public String getInstructor(@PathVariable Long id,Model model){
        Instructor instructor = instructorService.getInstructorById(id);
        model.addAttribute("courses",instructor.getCourses());
        return "instructor/getInstructor";
    }




}
