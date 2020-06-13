package xyz.yangzhe.crowd.exception;

/**
 * @Description: 登陆失败异常
 * @Author: Yangzhe
 * @Data: 2020/6/12
 */
public class LoginFailedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LoginFailedException() {
        super();
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(Throwable cause) {
        super(cause);
    }

    public LoginFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
