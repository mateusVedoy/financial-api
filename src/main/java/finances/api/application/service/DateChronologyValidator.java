package finances.api.application.service;

import finances.api.domain.port.IValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DateChronologyValidator implements IValidator<LocalDate> {

    @Override
    public boolean isValid(List<LocalDate> objects) {
        if(isListEmpty(objects))
            return false;

        for(int x = 0; x < objects.size(); x++){
            if(!isDatesChronologyValid(objects.get(x), objects.get(++x)))
                return false;
        }

        return true;
    }
    private boolean isListEmpty(List<LocalDate> list) {
        return list.isEmpty();
    }
    private boolean isDatesChronologyValid(LocalDate initialDate, LocalDate finalDate) {
        return initialDate.isBefore(finalDate);
    }
}
