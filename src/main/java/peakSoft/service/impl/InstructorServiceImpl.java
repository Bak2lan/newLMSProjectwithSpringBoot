package peakSoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peakSoft.entity.Company;
import peakSoft.entity.Course;
import peakSoft.entity.Instructor;
import peakSoft.repository.CompanyRepo;
import peakSoft.repository.CourseRepo;
import peakSoft.repository.InstructorRepo;
import peakSoft.service.InstructorService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepo instructorRepo;
    private final CompanyRepo companyRepo;
    private final CourseRepo courseRepo;

    @Override
    public void save(Instructor instructor) {
        instructorRepo.save(instructor);
    }

    @Override
    public void assignToCompany(Long companyId, Instructor instructor) {
        Company company = companyRepo.getCompanyById(companyId);
        instructorRepo.save(instructor);

        Set<Instructor> instructors = company.getInstructors();

        if (instructors==null){
            instructors=new LinkedHashSet<>();
            company.setInstructors(instructors);
        }
        instructors.add(instructor);
        companyRepo.save(company);




    }

    @Override
    public List<Instructor> getAllInstructors() {
       return instructorRepo.findAll();
    }

    @Override
    public Instructor getInstructorById(Long id) {
        return instructorRepo.getInstructorById(id);
    }

    @Override
    public void deleteInstructor(Long id) {
        Instructor instructor = getInstructorById(id);
        Set<Company> companies = instructor.getCompanies();
        for(Company company:companies){
            company.getInstructors().remove(instructor);
            companyRepo.save(company);
        }
        Set<Course> courses = instructor.getCourses();
        if (courses!=null){
            for (Course course:courses){
                course.setInstructor(null);
                courseRepo.save(course);
            }
        }
        instructorRepo.deleteById(instructor.getId());
    }

    @Override
    public void update(Long id, Instructor newInstructor) {
        Instructor instructor = getInstructorById(id);
        instructor.setFirstName(newInstructor.getFirstName());
        instructor.setLastName(newInstructor.getLastName());
        instructor.setPhoneNumber(newInstructor.getPhoneNumber());
        instructor.setSpecialization(newInstructor.getSpecialization());
    }

    @Override
    public List<Course> getAllInstructorsCourses(Long id) {
        return instructorRepo.getAllInstructorCourses(id);
    }

    @Override
    public List<Instructor> getInstructorsByCompanyId(Long companyId) {
        return instructorRepo.getInstructorByCompaniesId(companyId);
    }

}
