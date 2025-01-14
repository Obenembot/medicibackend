package za.co.medici.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoAuditEntityException extends Exception {
    private long status;

    public NoAuditEntityException(String message, long status) {
        super(message);
        this.status = status;
    }
}
