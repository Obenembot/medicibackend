package za.co.medici.uitls;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailValidatorUtil {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public boolean isValidEmail(String email) {
        if (ObjectUtils.isEmpty(email)) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
