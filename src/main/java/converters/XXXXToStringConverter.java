package converters;


import domain.Audit;
import domain.XXXX;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class XXXXToStringConverter implements Converter<XXXX, String> {

    @Override
    public String convert(final XXXX xxxx) {
        String result;

        if (xxxx == null)
        result = null;
        else
        result = String.valueOf(xxxx.getId());

        return result;
        }
}
