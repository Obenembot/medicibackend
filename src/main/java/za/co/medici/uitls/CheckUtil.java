package za.co.medici.uitls;

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

    public boolean notEqual(Object value1, Object value2) {
        return ObjectUtils.notEqual(value1, value2);
    }
}
