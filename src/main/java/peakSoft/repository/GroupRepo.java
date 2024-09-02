package peakSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peakSoft.entity.Group;

import java.util.List;

@Repository
public interface GroupRepo extends JpaRepository<Group,Long> {

    @Query("select g from Group g join g.courses gc join gc.company gcc where gcc.id=:companyId")
    List<Group>getGroupsByCompanyId(Long companyId);
}
