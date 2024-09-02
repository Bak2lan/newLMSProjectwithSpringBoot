package peakSoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peakSoft.entity.Course;
import peakSoft.entity.Group;
import peakSoft.exception.MyException;
import peakSoft.repository.CourseRepo;
import peakSoft.repository.GroupRepo;
import peakSoft.service.GroupService;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepo groupRepo;
    private final CourseRepo courseRepo;

    @Override
    public void saveGroup(Group group) {
            groupRepo.save(group);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepo.findAll();
    }

    @Override
    public Group getById(Long id) {
        return groupRepo.findById(id).orElseThrow(
                ()->new NoSuchElementException(String.format("Not found group with %s id",id)));
    }

    @Override
    public void updateGroup(Long id, Group newGroup) {
        Group group = getById(id);
        group.setGroupName(newGroup.getGroupName());
        group.setDescription(newGroup.getDescription());
        group.setImageLink(newGroup.getImageLink());
    }

    @Override
    public void deleteGroup(Long id) {
        try {
            Group group = getById(id);
            if (group == null) {
                throw new MyException("Not found course Id");
            }
            for (Course course: group.getCourses()){
                course.getGroups().remove(group);
                courseRepo.save(course);
            }

            groupRepo.deleteById(group.getId());
        }catch (MyException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveGroupToCourse(Long courseId, Group group) {
        Set<Group>groups= new HashSet<>();
        List<Course>courses=new ArrayList<>();
        groups.add(group);
        Course course = courseRepo.findById(courseId).orElseThrow(
                () -> new NoSuchElementException(String.format("not found course with %s id", courseId))
        );
        courses.add(course);
       course.setGroups(groups);
       group.setCourses(courses);
       saveGroup(group);

    }

    @Override
    public List<Group> getGroupByCompanyId(Long companyId) {
     return   groupRepo.getGroupsByCompanyId(companyId);
    }
}
