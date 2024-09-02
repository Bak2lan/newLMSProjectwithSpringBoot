package peakSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peakSoft.entity.Student;

@Repository
public interface StudentRepo  extends JpaRepository<Student,Long> {
    @Query("select s from Student s where s.id=:studentId")
    Student getStudentById(Long studentId);

}
