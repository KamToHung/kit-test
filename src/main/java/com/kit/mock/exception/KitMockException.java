package com.kit.mock.exception;

/**
 * 异常
 *
 * @author <a href = "kamtohung@gmail.com">hongjintao</a>
 * @since 2022/3/24 20:45
 */
public class KitMockException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public KitMockException(String message) {
        super(message);
    }

    public KitMockException(String message, Throwable cause) {
        super(message, cause);
    }
}
