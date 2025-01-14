package za.co.medici.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserException extends Exception {
    private long status;

    public UserException(String message, long status) {
        super(message);
        this.status = status;
    }
}
