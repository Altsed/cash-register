package validation;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class main {


    public static void main(String[] args) {
        @NotNull(message = "REQUIRED not empty")
        String s = null;
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        validator.validate(s);
    }
}
