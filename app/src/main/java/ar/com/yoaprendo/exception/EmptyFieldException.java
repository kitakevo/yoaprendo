package ar.com.yoaprendo.exception;

public class EmptyFieldException extends Exception{

    @Override
    public String getMessage() {
        return "Asegurate de no dejar campos vacíos";
    }

}
