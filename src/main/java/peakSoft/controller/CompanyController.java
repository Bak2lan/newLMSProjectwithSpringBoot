package peakSoft.controller;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peakSoft.entity.Company;
import peakSoft.service.CompanyService;
import peakSoft.service.CourseService;
import peakSoft.service.InstructorService;

@Transactional
@Controller
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final CourseService courseService;
    private final InstructorService instructorService;

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("allCompanies",companyService.getAllCompany());
        model.addAttribute("AllInstructors",instructorService.getAllInstructors());
        return "company/mainPage";
    }

    @GetMapping("/new")
    public String newCompany(Model model){
        model.addAttribute("newCompany",new Company());
        return "company/newCompany";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("newCompany")Company company){
        companyService.saveCompany(company);
        return "redirect:/companies";
    }

    @GetMapping("{id}/form")
    public String formUpdate(@PathVariable Long id,Model model){
        model.addAttribute("updatedCompany",companyService.getById(id));
        return "company/formCompany";
    }

    @PostMapping("{id}/update")
    public String update(@ModelAttribute("updatedCompany")Company company,@PathVariable Long id){
        companyService.updateCompany(id,company);
        return "redirect:/companies";
    }
    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id){
        companyService.deleteCompany(id);
        return "redirect:/companies";
    }

    @GetMapping("{id}/get")
    public String get(Model model,@PathVariable Long id){
        Company company = companyService.getById(id);
        model.addAttribute("courses",company.getCourses());
        model.addAttribute("getCompany",companyService.getById(id));
        return "company/getCompany";
    }



}
