package superapp.logic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)  // 400
public class DeprecatedOperationException extends RuntimeException {
    private final Log logger = LogFactory.getLog(DeprecatedOperationException.class);

    public DeprecatedOperationException() {
    }

    public DeprecatedOperationException(String message) {
        super(message);
        this.logger.warn(message);
    }

    public DeprecatedOperationException(Throwable cause) {
        super(cause);
    }

    public DeprecatedOperationException(String message, Throwable cause) {
        super(message, cause);
        this.logger.warn(message);
    }
}