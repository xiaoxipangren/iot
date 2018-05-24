package com.nationalchip.iot.data.exception;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 2:26 PM
 * @Modified:
 */
public class HashNotMatchedException extends DataException {

    private static final long serialVersionUID = 1L;

    public static final String SHA1 = "SHA-1";
    public static final String MD5 = "MD5";

    private final String hashFunction;

    /**
     * Constructs a HashNotMatchException with message and cause.
     *
     * @param message
     *            the message of the exception
     * @param cause
     *            the cause of the exception
     * @param hashFunction
     *            the hash function which caused this exception
     */
    public HashNotMatchedException(final String message, final Throwable cause, final String hashFunction) {
        super(message, cause);
        this.hashFunction = hashFunction;
    }

    /**
     * Constructs a HashNotMatchException with message.
     *
     * @param message
     *            the message of the exception
     * @param hashFunction
     *            the hash function which caused this exception
     */
    public HashNotMatchedException(final String message, final String hashFunction) {
        super(message);
        this.hashFunction = hashFunction;
    }

    /**
     * @return the hashFunction
     */
    public String getHashFunction() {
        return hashFunction;
    }
}