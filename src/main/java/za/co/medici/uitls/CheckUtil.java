package za.co.medici.uitls;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

@Component
public class CheckUtil {
    public boolean isEmpty(@NotNull Object value) {
        return ObjectUtils.isEmpty(value);
    }
}
