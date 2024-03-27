package superapp.logic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)    // 404
public class UserNotFoundException extends RuntimeException {
    private final Log logger = LogFactory.getLog(UserNotFoundException.class);

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
        this.logger.warn(message);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.logger.warn(message);
    }

}
