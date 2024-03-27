package superapp.logic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED) // 401
public class UserNotAuthorizedException extends RuntimeException {
    private final Log logger = LogFactory.getLog(UserNotAuthorizedException.class);

    public UserNotAuthorizedException() {
    }

    public UserNotAuthorizedException(String message) {
        super(message);
        this.logger.warn(message);
    }

    public UserNotAuthorizedException(Throwable cause) {
        super(cause);
    }

    public UserNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
        this.logger.warn(message);
    }
}
