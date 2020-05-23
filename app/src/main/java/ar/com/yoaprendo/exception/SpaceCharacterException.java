package ar.com.yoaprendo.exception;

public class SpaceCharacterException extends Exception{

    @Override
    public String getMessage() {
        return "Asegurate de usar espacios";
    }

}
