package org.lab6.exception;

/**
 * Exception that occurs when provided value violates class contract
 */
public class IllegalFieldException extends RuntimeException {

    /**
     * Creates new instance of <i>IllegalFieldException</i>
     * @param field field that is violated
     * @param obj class instance
     */
    public IllegalFieldException(String field, Object obj) {
        super("Invalid value for field " + field + " for " + obj.getClass().toString());
    }
}
