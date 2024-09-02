package peakSoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peakSoft.entity.Company;
import peakSoft.entity.Instructor;
import peakSoft.repository.CompanyRepo;
import peakSoft.repository.InstructorRepo;
import peakSoft.service.CompanyService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepo companyRepo;
    private final InstructorRepo instructorRepo;


    @Override
    public void saveCompany(Company company){
        companyRepo.save(company);
    }

    @Override
    public List<Company> getAllCompany(){
        return companyRepo.findAll();
    }

    @Override
    public Company getById(Long id) {
     return   companyRepo.getCompanyById(id);
    }

    @Override
    public void updateCompany(Long id, Company newCompany) {
        Company company = getById(id);
        company.setName(newCompany.getName());
        company.setAddress(newCompany.getAddress());
        company.setImage(newCompany.getImage());
        company.setPhoneNumber(newCompany.getPhoneNumber());
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepo.deleteById(id);
    }

    @Override
    public void assignInstructorToCompany(Long instructorId, Long companyId) {
        Instructor instructor = instructorRepo.getInstructorById(instructorId);
        Company company = companyRepo.getCompanyById(companyId);

        Set<Instructor> instructors = company.getInstructors();
        if (instructors==null){
            instructors= new HashSet<>();
        }
        instructors.add(instructor);
        company.setInstructors(instructors);

        Set<Company> companies = instructor.getCompanies();
        if (companies==null){
            companies= new HashSet<>();
        }
        companies.add(company);
        instructor.setCompanies(companies);


    }
}
