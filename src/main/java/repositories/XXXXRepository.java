package repositories;

import domain.Audit;
import domain.Position;
import domain.XXXX;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface XXXXRepository extends JpaRepository<XXXX, Integer> {

    @Query("select x from XXXX x where x.isFinal=true and x.audit.auditor.id=?1")
    Collection<XXXX> getXXXXsFinalByAuditor(int auditorId);

    @Query("select x from XXXX x where x.audit.id=?1")
    Collection<XXXX> getXXXXsByAudit(int auditId);

}
