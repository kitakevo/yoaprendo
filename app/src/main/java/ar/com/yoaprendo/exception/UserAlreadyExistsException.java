package ar.com.yoaprendo.exception;

public class UserAlreadyExistsException extends Exception {

    @Override
    public String getMessage() {
        return "El nombre de usuario ya existe";
    }

}
