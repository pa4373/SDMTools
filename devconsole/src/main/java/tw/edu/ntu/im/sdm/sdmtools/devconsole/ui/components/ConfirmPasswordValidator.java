package tw.edu.ntu.im.sdm.sdmtools.devconsole.ui.components;

import com.vaadin.data.Validator;
import com.vaadin.ui.PasswordField;


public class ConfirmPasswordValidator implements Validator {

    String errorMessage;
    PasswordField originalPasswordField;

    public ConfirmPasswordValidator(String errorMessage, PasswordField originalPasswordField) {
        this.errorMessage = errorMessage;
        this.originalPasswordField = originalPasswordField;
    }

    @Override
    public void validate(Object value) {
        if (value instanceof String) {
            if (value.equals(originalPasswordField.getValue())) {
                return;
            }
        }
        throw new Validator.InvalidValueException(this.errorMessage);
    }

}
