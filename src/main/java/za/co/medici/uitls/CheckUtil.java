package za.co.medici.uitls;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

@Component
public class CheckUtil {
    public boolean isEmpty(Object value) {
        return ObjectUtils.isEmpty(value);
    }

    public boolean notEqual(Long value1, Long value2) {
        return ObjectUtils.notEqual(value1, value2);
    }
}
