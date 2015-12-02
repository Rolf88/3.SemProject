/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author RolfMoikjær
 */
public class ErrorMessage {

    private int errorCode;
    private int httpError;
    private String message;

    public ErrorMessage(Throwable ex, int errorCode, int httpError) {
        this.errorCode = errorCode;
        this.httpError = httpError;
        this.message = ex.getMessage();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getHttpError() {
        return httpError;
    }

    public void setHttpError(int httpError) {
        this.httpError = httpError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
