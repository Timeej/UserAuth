package rs.ac.singidunum.icr.validation;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

import com.google.common.base.Joiner;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        //ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final String password, ConstraintValidatorContext context) {
        final PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new WhitespaceRule()
        ));
        final RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(Joiner.on(",").join(validator.getMessages(result))).addConstraintViolation();
        return false;
    }


}
