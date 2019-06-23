
package controllers.xxxx;

import controllers.AbstractController;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import javax.jws.WebParam;
import javax.validation.ValidationException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;


@Controller
@RequestMapping("xxxx")
public class XXXXController extends AbstractController {

    @Autowired
    private XXXXService xxxxService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private CompanyService companyService;

    @ExceptionHandler(BindException.class)
    public ModelAndView handleMismatchException(final BindException oops) {
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value="/company/list", method = RequestMethod.GET)
    public ModelAndView listCompany(@RequestParam int auditId){
        ModelAndView result;
        Collection<XXXX> xxxxs;

        try {
            xxxxs = this.xxxxService.getXXXXsByAudit(auditId);
            Position position = this.auditService.getPositionByAudit(auditId);
            Assert.isTrue(position.getCompany().equals(actorService.getActorLogged()));
            final Date haceUnMes = this.restarMesesFecha(new Date(), 1);
            final Date haceDosMeses = this.restarMesesFecha(new Date(), 2);
            final String language = LocaleContextHolder.getLocale().getLanguage();

            result = new ModelAndView("xxxx/company/list");
            result.addObject("xxxxs", xxxxs);
            result.addObject("requestURI", "xxxx/company/list.do");
            result.addObject("haceUnMes", haceUnMes);
            result.addObject("haceDosMeses", haceDosMeses);
            result.addObject("lang", language);
        }catch(Throwable oops){
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    @RequestMapping(value="/auditor/list", method = RequestMethod.GET)
    public ModelAndView listAuditor(){
        ModelAndView result;
        Collection<XXXX> xxxxs;

        xxxxs = this.xxxxService.getXXXXsFinalByAuditor();
        final Date haceUnMes = this.restarMesesFecha(new Date(), 1);
        final Date haceDosMeses = this.restarMesesFecha(new Date(), 2);
        final String language = LocaleContextHolder.getLocale().getLanguage();

        result = new ModelAndView("xxxx/auditor/list");
        result.addObject("xxxxs", xxxxs);
        result.addObject("requestURI", "xxxx/auditor/list.do");
        result.addObject("haceUnMes", haceUnMes);
        result.addObject("haceDosMeses", haceDosMeses);
        result.addObject("lang", language);

        return result;
    }

    @RequestMapping(value="/company/show", method = RequestMethod.GET)
    public ModelAndView showCompany(@RequestParam int xxxxId){
        ModelAndView result;
        XXXX xxxx;

        try{
            xxxx = this.xxxxService.findOne(xxxxId);
            Position position = this.auditService.getPositionByAudit(xxxx.getAudit().getId());
            Assert.isTrue(position.getCompany().equals(this.actorService.getActorLogged().getId()));
            final String language = LocaleContextHolder.getLocale().getLanguage();
            final SimpleDateFormat formatterEs = new SimpleDateFormat("dd-MM-yy HH:mm");
            final SimpleDateFormat formatterEn = new SimpleDateFormat("yy/MM/dd HH:mm");
            String moment;
            if (language == "es")
                moment = formatterEs.format(xxxx.getMoment());
            else
                moment = formatterEn.format(xxxx.getMoment());

            result = new ModelAndView("xxxx/company/show");
            result.addObject("xxxx", xxxx);
            result.addObject("moment", moment);
        }catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    @RequestMapping(value="/auditor/show", method = RequestMethod.GET)
    public ModelAndView showAuditor(@RequestParam int xxxxId){
        ModelAndView result;
        XXXX xxxx;

        try{
            xxxx = this.xxxxService.findOne(xxxxId);
            Assert.isTrue(xxxx.getAudit().getAuditor().equals(this.actorService.getActorLogged().getId()));
            final String language = LocaleContextHolder.getLocale().getLanguage();
            final SimpleDateFormat formatterEs = new SimpleDateFormat("dd-MM-yy HH:mm");
            final SimpleDateFormat formatterEn = new SimpleDateFormat("yy/MM/dd HH:mm");
            String moment;
            if (language == "es")
                moment = formatterEs.format(xxxx.getMoment());
            else
                moment = formatterEn.format(xxxx.getMoment());
            result = new ModelAndView("xxxx/auditor/show");
            result.addObject("xxxx", xxxx);
            result.addObject("moment", moment);
        }catch (Throwable oops){
            final Date haceUnMes = this.restarMesesFecha(new Date(), 1);
            final Date haceDosMeses = this.restarMesesFecha(new Date(), 2);
            final String language = LocaleContextHolder.getLocale().getLanguage();
            result = new ModelAndView("redirect:/xxxx/auditor/list.do");
            result.addObject("haceUnMes", haceUnMes);
            result.addObject("haceDosMeses", haceDosMeses);
            result.addObject("lang", language);
        }
        return result;
    }

    @RequestMapping(value="/company/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam int auditId){
        ModelAndView result;
        Audit audit;
        XXXX xxxx;

        try{
            audit = this.auditService.findOne(auditId);
            Position position = this.auditService.getPositionByAudit(auditId);
            Company company = this.companyService.findOne(actorService.getActorLogged().getId());
            Assert.isTrue(position.getCompany().equals(company));
            Assert.isTrue(audit.getIsFinal());
            Assert.isTrue(position.getIsFinal());
            Assert.isTrue(!position.getIsCancelled());

            xxxx = this.xxxxService.create();

            result = new ModelAndView("xxxx/company/edit");
            result.addObject("xxxx", xxxx);
            result.addObject("auditId", auditId);
        }catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    @RequestMapping(value="/company/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int xxxxId){
        ModelAndView result;
        XXXX xxxx;

        try{
            xxxx = this.xxxxService.findOne(xxxxId);
            Audit audit = xxxx.getAudit();
            Company company = this.companyService.findOne(actorService.getActorLogged().getId());
            Position position = this.auditService.getPositionByAudit(audit.getId());
            Assert.isTrue(position.getCompany().equals(company));
            Assert.isTrue(!xxxx.getIsFinal());

            result = new ModelAndView("xxxx/company/edit");
            result.addObject("xxxx", xxxx);
            result.addObject("auditId", audit.getId());
        } catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    @RequestMapping(value="/company/edit", method = RequestMethod.POST, params="save")
    public ModelAndView save(@ModelAttribute("xxxx") XXXX xxxx, @RequestParam int auditId, BindingResult binding){
        ModelAndView result;

        try{
            xxxx = this.xxxxService.reconstruct(xxxx, auditId, binding);
            this.xxxxService.save(xxxx, auditId);
            final Date haceUnMes = this.restarMesesFecha(new Date(), 1);
            final Date haceDosMeses = this.restarMesesFecha(new Date(), 2);
            final String language = LocaleContextHolder.getLocale().getLanguage();
            result = new ModelAndView("redirect:/xxxx/company/list.do?auditId="+auditId);
            result.addObject("haceUnMes", haceUnMes);
            result.addObject("haceDosMeses", haceDosMeses);
            result.addObject("lang", language);
        } catch(ValidationException v){
            result = new ModelAndView("xxxx/company/edit");
            for (final ObjectError e : binding.getAllErrors())
                if (e.getDefaultMessage().equals("URL incorrecta") || e.getDefaultMessage().equals("Invalid URL"))
                    result.addObject("pictureError", e.getDefaultMessage());
            result.addObject("xxxx", xxxx);
            result.addObject("auditId", auditId);
        } catch(Throwable oops){
            result = new ModelAndView("xxxx/company/edit");
            result.addObject("xxxx", xxxx);
            result.addObject("auditId", auditId);
            result.addObject("message", "xxxx.commit.error");
        }
        return result;
    }

    @RequestMapping(value = "/company/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam int xxxxId){
        ModelAndView result;

        try{
            XXXX xxxx = this.xxxxService.findOne(xxxxId);
            Audit audit = xxxx.getAudit();
            final Date haceUnMes = this.restarMesesFecha(new Date(), 1);
            final Date haceDosMeses = this.restarMesesFecha(new Date(), 2);
            final String language = LocaleContextHolder.getLocale().getLanguage();
            this.xxxxService.delete(xxxxId);
            result = new ModelAndView("redirect:/xxxx/company/list.do?auditId="+audit.getId());
            result.addObject("haceUnMes", haceUnMes);
            result.addObject("haceDosMeses", haceDosMeses);
            result.addObject("lang", language);
        }catch (Throwable oops){
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