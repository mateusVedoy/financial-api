package finances.api.application.service;

import finances.api.domain.port.IValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DatePatternValidator implements IValidator<String> {

    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
    @Override
    public boolean isValid(List<String> objects) {
        if(isListEmpty(objects))
            return false;

        for (String object: objects) {
            if(!isDatePatternValid(object))
                return false;
        }
        return true;
    }
    private boolean isDatePatternValid(String date) {
        Pattern pattern = Pattern.compile(DATE_REGEX);
        Matcher dateMatcher = pattern.matcher(date);
        return dateMatcher.matches();
    }
    private boolean isListEmpty(List<String> list) {
        return list.isEmpty();
    }
}
