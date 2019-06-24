
package controllers.xxxx;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuditService;
import services.CompanyService;
import services.XXXXService;
import controllers.AbstractController;
import domain.Audit;
import domain.Company;
import domain.Position;
import domain.XXXX;

@Controller
@RequestMapping("mokejima")
public class XXXXController extends AbstractController {

	@Autowired
	private XXXXService		xxxxService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private AuditService	auditService;

	@Autowired
	private CompanyService	companyService;


	@ExceptionHandler(BindException.class)
	public ModelAndView handleMismatchException(final BindException oops) {
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/company/list", method = RequestMethod.GET)
	public ModelAndView listCompany(@RequestParam final int auditId) {
		ModelAndView result;
		Collection<XXXX> xxxxs;

		try {
			xxxxs = this.xxxxService.getXXXXsByAudit(auditId);
			final Position position = this.auditService.getPositionByAudit(auditId);
			Assert.isTrue(position.getCompany().equals(this.actorService.getActorLogged()));
			final Date haceUnMes = this.restarMesesFecha(new Date(), 1);
			final Date haceDosMeses = this.restarMesesFecha(new Date(), 2);
			final String language = LocaleContextHolder.getLocale().getLanguage();

			result = new ModelAndView("mokejima/company/list");
			result.addObject("xxxxs", xxxxs);
			result.addObject("requestURI", "mokejima/company/list.do");
			result.addObject("haceUnMes", haceUnMes);
			result.addObject("haceDosMeses", haceDosMeses);
			result.addObject("lang", language);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	@RequestMapping(value = "/auditor/list", method = RequestMethod.GET)
	public ModelAndView listAuditor() {
		ModelAndView result;
		Collection<XXXX> xxxxs;

		xxxxs = this.xxxxService.getXXXXsFinalByAuditor();
		final Date haceUnMes = this.restarMesesFecha(new Date(), 1);
		final Date haceDosMeses = this.restarMesesFecha(new Date(), 2);
		final String language = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("mokejima/auditor/list");
		result.addObject("xxxxs", xxxxs);
		result.addObject("requestURI", "mokejima/auditor/list.do");
		result.addObject("haceUnMes", haceUnMes);
		result.addObject("haceDosMeses", haceDosMeses);
		result.addObject("lang", language);

		return result;
	}

	@RequestMapping(value = "/company/show", method = RequestMethod.GET)
	public ModelAndView showCompany(@RequestParam final int mokejimaId) {
		ModelAndView result;
		XXXX xxxx;

		try {
			xxxx = this.xxxxService.findOne(mokejimaId);
			final Position position = this.auditService.getPositionByAudit(xxxx.getAudit().getId());
			Assert.isTrue(position.getCompany().equals(this.actorService.getActorLogged().getId()));
			final String language = LocaleContextHolder.getLocale().getLanguage();
			final SimpleDateFormat formatterEs = new SimpleDateFormat("dd-MM-yy HH:mm");
			final SimpleDateFormat formatterEn = new SimpleDateFormat("yy/MM/dd HH:mm");
			String moment;
			if (language == "es")
				moment = formatterEs.format(xxxx.getMoment());
			else
				moment = formatterEn.format(xxxx.getMoment());

			result = new ModelAndView("mokejima/company/show");
			result.addObject("xxxx", xxxx);
			result.addObject("moment", moment);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/auditor/show", method = RequestMethod.GET)
	public ModelAndView showAuditor(@RequestParam final int mokejimaId) {
		ModelAndView result;
		XXXX xxxx;

		try {
			xxxx = this.xxxxService.findOne(mokejimaId);
			Assert.isTrue(xxxx.getAudit().getAuditor().equals(this.actorService.getActorLogged().getId()));
			final String language = LocaleContextHolder.getLocale().getLanguage();
			final SimpleDateFormat formatterEs = new SimpleDateFormat("dd-MM-yy HH:mm");
			final SimpleDateFormat formatterEn = new SimpleDateFormat("yy/MM/dd HH:mm");
			String moment;
			if (language == "es")
				moment = formatterEs.format(xxxx.getMoment());
			else
				moment = formatterEn.format(xxxx.getMoment());
			result = new ModelAndView("mokejima/auditor/show");
			result.addObject("xxxx", xxxx);
			result.addObject("moment", moment);
		} catch (final Throwable oops) {
			final Date haceUnMes = this.restarMesesFecha(new Date(), 1);
			final Date haceDosMeses = this.restarMesesFecha(new Date(), 2);
			final String language = LocaleContextHolder.getLocale().getLanguage();
			result = new ModelAndView("redirect:/mokejima/auditor/list.do");
			result.addObject("haceUnMes", haceUnMes);
			result.addObject("haceDosMeses", haceDosMeses);
			result.addObject("lang", language);
		}
		return result;
	}

	@RequestMapping(value = "/company/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int auditId) {
		ModelAndView result;
		Audit audit;
		XXXX xxxx;

		try {
			audit = this.auditService.findOne(auditId);
			final Position position = this.auditService.getPositionByAudit(auditId);
			final Company company = this.companyService.findOne(this.actorService.getActorLogged().getId());
			Assert.isTrue(position.getCompany().equals(company));
			Assert.isTrue(audit.getIsFinal());
			Assert.isTrue(position.getIsFinal());
			Assert.isTrue(!position.getIsCancelled());

			xxxx = this.xxxxService.create();

			result = new ModelAndView("mokejima/company/edit");
			result.addObject("xxxx", xxxx);
			result.addObject("auditId", auditId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/company/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int mokejimaId) {
		ModelAndView result;
		XXXX xxxx;

		try {
			xxxx = this.xxxxService.findOne(mokejimaId);
			final Audit audit = xxxx.getAudit();
			final Company company = this.companyService.findOne(this.actorService.getActorLogged().getId());
			final Position position = this.auditService.getPositionByAudit(audit.getId());
			Assert.isTrue(position.getCompany().equals(company));
			Assert.isTrue(!xxxx.getIsFinal());

			result = new ModelAndView("mokejima/company/edit");
			result.addObject("xxxx", xxxx);
			result.addObject("auditId", audit.getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	@RequestMapping(value = "/company/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("xxxx") XXXX xxxx, @RequestParam final int auditId, final BindingResult binding) {
		ModelAndView result;

		try {
			xxxx = this.xxxxService.reconstruct(xxxx, auditId, binding);
			this.xxxxService.save(xxxx, auditId);
			final Date haceUnMes = this.restarMesesFecha(new Date(), 1);
			final Date haceDosMeses = this.restarMesesFecha(new Date(), 2);
			final String language = LocaleContextHolder.getLocale().getLanguage();
			result = new ModelAndView("redirect:/mokejima/company/list.do?auditId=" + auditId);
			result.addObject("haceUnMes", haceUnMes);
			result.addObject("haceDosMeses", haceDosMeses);
			result.addObject("lang", language);
		} catch (final ValidationException v) {
			result = new ModelAndView("mokejima/company/edit");
			for (final ObjectError e : binding.getAllErrors())
				if (e.getDefaultMessage().equals("URL incorrecta") || e.getDefaultMessage().equals("Invalid URL"))
					result.addObject("pictureError", e.getDefaultMessage());
			result.addObject("xxxx", xxxx);
			result.addObject("auditId", auditId);
		} catch (final Throwable oops) {
			result = new ModelAndView("mokejima/company/edit");
			result.addObject("xxxx", xxxx);
			result.addObject("auditId", auditId);
			result.addObject("message", "xxxx.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/company/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int mokejimaId) {
		ModelAndView result;

		try {
			final XXXX xxxx = this.xxxxService.findOne(mokejimaId);
			final Audit audit = xxxx.getAudit();
			final Date haceUnMes = this.restarMesesFecha(new Date(), 1);
			final Date haceDosMeses = this.restarMesesFecha(new Date(), 2);
			final String language = LocaleContextHolder.getLocale().getLanguage();
			this.xxxxService.delete(mokejimaId);
			result = new ModelAndView("redirect:/mokejima/company/list.do?auditId=" + audit.getId());
			result.addObject("haceUnMes", haceUnMes);
			result.addObject("haceDosMeses", haceDosMeses);
			result.addObject("lang", language);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	private Date restarMesesFecha(final Date date, final Integer meses) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -meses);
		return calendar.getTime();
	}
}
