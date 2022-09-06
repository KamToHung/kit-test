package com.kit.junit.exception;

/**
 * 异常
 *
 * @author <a href = "kamtohung@gmail.com">hongjintao</a>
 * @since 2022/3/24 20:45
 */
public class KitJUnitException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public KitJUnitException(String message) {
        super(message);
    }

    public KitJUnitException(String message, Throwable cause) {
        super(message, cause);
    }
}
