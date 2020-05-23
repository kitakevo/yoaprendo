package ar.com.yoaprendo.exception;

public class PasswordMismatchException extends Exception {

    @Override
    public String getMessage() {
        return "Las claves no coinciden";
    }

}
