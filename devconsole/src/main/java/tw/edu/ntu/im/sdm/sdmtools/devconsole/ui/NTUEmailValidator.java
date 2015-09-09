package tw.edu.ntu.im.sdm.sdmtools.devconsole.ui;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;

public class NTUEmailValidator extends EmailValidator {

    public NTUEmailValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public void validate(Object value) {
        super.validate(value);
        if (value instanceof String) {
            if (((String) value).matches("^(\\S+)@ntu.edu.tw$")) {
                return;
            }
        }
        throw new Validator.InvalidValueException(super.getErrorMessage());
    }
}
