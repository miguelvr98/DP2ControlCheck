package converters;

import domain.Audit;
import domain.XXXX;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import repositories.AuditRepository;
import repositories.XXXXRepository;

public class StringToXXXXConverter implements Converter<String, XXXX> {

    @Autowired
    XXXXRepository xxxxRepository;


    @Override
    public XXXX convert(final String text) {
        XXXX result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = this.xxxxRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }
}
