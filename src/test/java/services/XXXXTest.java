package services;

import domain.Audit;
import domain.XXXX;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.DataBinder;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class XXXXTest extends AbstractTest {

    @Autowired
    private XXXXService xxxxService;

    @Autowired
    private AuditService auditService;


    @Test
    public void createXXXXXDriver() {
        final Object testingData[][] = {
                {
                        "prueba", "http://picture.com", "company1", "audit1", null
                }, {
                "", "http://picture.com", "company1", "audit1", ValidationException.class
        }
        };
        for (int i = 0; i < testingData.length; i++)
            this.createXXXXTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], super.getEntityId((String) testingData[i][3]), (Class<?>) testingData[i][4]);
    }

    private void createXXXXTemplate(final String body, final String picture, final String company, final int auditId, final Class<?> expected) {
        Class<?> caught;
        caught = null;

        try {
            this.authenticate(company);
            final Audit audit = this.auditService.findOne(auditId);
            XXXX result = this.xxxxService.create();
            result.setBody(body);
            result.setPicture(picture);
            result.setAudit(audit);
            final DataBinder binding = new DataBinder(new XXXX());
            result = this.xxxxService.reconstruct(result, audit.getId(), binding.getBindingResult());
            this.xxxxService.save(result, auditId);

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }

        super.checkExceptions(expected, caught);
    }

}