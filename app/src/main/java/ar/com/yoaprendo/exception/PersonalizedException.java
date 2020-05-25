package ar.com.yoaprendo.exception;

public class PersonalizedException extends Exception {

    private String msg = "Error";

    public PersonalizedException(String msg){
        this.msg=msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }

}
