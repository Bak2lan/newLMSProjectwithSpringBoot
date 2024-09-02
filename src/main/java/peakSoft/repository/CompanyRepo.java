package peakSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peakSoft.entity.Company;

@Repository
public interface CompanyRepo extends JpaRepository<Company,Long> {

    @Query("select c from Company c where c.id=:id")
    Company getCompanyById(Long id);

}
