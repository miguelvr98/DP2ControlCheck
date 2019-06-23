package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.AuditRepository;
import repositories.XXXXRepository;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

@Service
@Transactional
public class XXXXService {

    @Autowired
    private XXXXRepository xxxxRepository;
    @Autowired
    private ActorService actorService;
    @Autowired
    private AuditService auditService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private Validator validator;

    public XXXX reconstruct(final XXXX xxxx, int auditId, final BindingResult binding) {
        XXXX result;
        if(xxxx.getId()==0) {
            result = this.create();
            result.setMoment(new Date());
            result.setTicker(this.tickerGenerator());
            result.setIsFinal(xxxx.getIsFinal());
            result.setBody(xxxx.getBody());
            result.setPicture(xxxx.getPicture());
            result.setAudit(this.auditService.findOne(auditId));
        } else{
            result = this.findOne(xxxx.getId());
            result.setBody(xxxx.getBody());
            result.setPicture(xxxx.getPicture());
            result.setIsFinal(xxxx.getIsFinal());
        }

        this.validator.validate(result, binding);
        if (binding.hasErrors())
            throw new ValidationException();
        return result;
    }

    public XXXX create(){
        Assert.isTrue(this.actorService.getActorLogged() instanceof Company);

        return new XXXX();
    }

    public XXXX findOne(int xxxxId){
        XXXX result;

        result = this.xxxxRepository.findOne(xxxxId);

        return result;
    }

    public Collection<XXXX> findAll(){
        Collection<XXXX> result;

        result = this.xxxxRepository.findAll();

        return result;
    }

    public XXXX save(XXXX xxxx, int auditId){
        Assert.isTrue(actorService.getActorLogged() instanceof Company);
        XXXX result;
        Position position;
        Audit audit = this.auditService.findOne(auditId);
        position = this.auditService.getPositionByAudit(auditId);
        Company company = this.companyService.findOne(actorService.getActorLogged().getId());
        Assert.isTrue(position.getCompany().equals(company));
        Assert.isTrue(audit.getIsFinal());
        Assert.isTrue(position.getIsFinal());
        Assert.isTrue(!position.getIsCancelled());

        result = this.xxxxRepository.save(xxxx);

        return result;
    }

    public void delete(int xxxxId){
        Assert.isTrue(actorService.getActorLogged() instanceof Company);
        XXXX xxxx = this.findOne(xxxxId);
        Position position = this.auditService.getPositionByAudit(xxxx.getAudit().getId());
        Assert.isTrue(!xxxx.getIsFinal());
        Assert.isTrue(position.getCompany().equals(this.companyService.findOne(actorService.getActorLogged().getId())));
        this.xxxxRepository.delete(xxxxId);
    }

    public void forceDelete(int xxxxId){
        this.xxxxRepository.delete(xxxxId);
    }

    public Collection<XXXX> getXXXXsFinalByAuditor(){
        Collection<XXXX> result;

        result = this.xxxxRepository.getXXXXsFinalByAuditor(this.actorService.getActorLogged().getId());

        return result;
    }

    public Collection<XXXX> getXXXXsByAudit(int auditId) {
        return this.xxxxRepository.getXXXXsByAudit(auditId);
    }

    private String tickerGenerator() {
        String dateRes = "";
        String numericRes = "";
        final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        dateRes = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());

        for (int i = 0; i < 5; i++) {
            final Random random = new Random();
            numericRes = numericRes + alphanumeric.charAt(random.nextInt(alphanumeric.length() - 1));
        }

        return dateRes + "-" + numericRes;
    }

}
