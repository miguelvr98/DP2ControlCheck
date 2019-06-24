
package services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.XXXXRepository;
import domain.Audit;
import domain.Company;
import domain.Position;
import domain.XXXX;

@Service
@Transactional
public class XXXXService {

	@Autowired
	private XXXXRepository	xxxxRepository;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private AuditService	auditService;
	@Autowired
	private CompanyService	companyService;
	@Autowired
	private Validator		validator;


	public XXXX reconstruct(final XXXX xxxx, final int auditId, final BindingResult binding) {
		XXXX result;
		if (xxxx.getId() == 0) {
			result = this.create();
			result.setMoment(new Date());
			result.setTicker(this.tickerGenerator());
			result.setIsFinal(xxxx.getIsFinal());
			result.setBody(xxxx.getBody());
			result.setPicture(xxxx.getPicture());
			result.setAudit(this.auditService.findOne(auditId));
		} else {
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

	public XXXX create() {
		Assert.isTrue(this.actorService.getActorLogged() instanceof Company);

		return new XXXX();
	}

	public XXXX findOne(final int xxxxId) {
		XXXX result;

		result = this.xxxxRepository.findOne(xxxxId);

		return result;
	}

	public Collection<XXXX> findAll() {
		Collection<XXXX> result;

		result = this.xxxxRepository.findAll();

		return result;
	}

	public XXXX save(final XXXX xxxx, final int auditId) {
		Assert.isTrue(this.actorService.getActorLogged() instanceof Company);
		XXXX result;
		Position position;
		final Audit audit = this.auditService.findOne(auditId);
		position = this.auditService.getPositionByAudit(auditId);
		final Company company = this.companyService.findOne(this.actorService.getActorLogged().getId());
		Assert.isTrue(position.getCompany().equals(company));
		Assert.isTrue(audit.getIsFinal());
		Assert.isTrue(position.getIsFinal());
		Assert.isTrue(!position.getIsCancelled());

		result = this.xxxxRepository.save(xxxx);

		return result;
	}

	public void delete(final int xxxxId) {
		Assert.isTrue(this.actorService.getActorLogged() instanceof Company);
		final XXXX xxxx = this.findOne(xxxxId);
		final Position position = this.auditService.getPositionByAudit(xxxx.getAudit().getId());
		Assert.isTrue(!xxxx.getIsFinal());
		Assert.isTrue(position.getCompany().equals(this.companyService.findOne(this.actorService.getActorLogged().getId())));
		this.xxxxRepository.delete(xxxxId);
	}

	public void forceDelete(final int xxxxId) {
		this.xxxxRepository.delete(xxxxId);
	}

	public Collection<XXXX> getXXXXsFinalByAuditor() {
		Collection<XXXX> result;

		result = this.xxxxRepository.getXXXXsFinalByAuditor(this.actorService.getActorLogged().getId());

		return result;
	}

	public Collection<XXXX> getXXXXsByAudit(final int auditId) {
		return this.xxxxRepository.getXXXXsByAudit(auditId);
	}

	private String tickerGenerator() {
		String yearRes = "";
		String monthRes = "";
		String dayRes = "";
		String numericRes = "";
		final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		yearRes = new SimpleDateFormat("yy").format(Calendar.getInstance().getTime());
		monthRes = new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
		dayRes = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());

		for (int i = 0; i < 5; i++) {
			final Random random = new Random();
			numericRes = numericRes + alphanumeric.charAt(random.nextInt(alphanumeric.length() - 1));
		}

		return yearRes + "/" + monthRes + "/" + dayRes + "-" + numericRes;
	}

}
