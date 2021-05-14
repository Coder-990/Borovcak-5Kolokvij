package main.java.hr.java.covidportal.iznimke;

public class BolestIstihSimptomaException extends RuntimeException {

    public BolestIstihSimptomaException(String message) {
        super(message);
    }

    public BolestIstihSimptomaException(Throwable cause) {
        super(cause);
    }

    public BolestIstihSimptomaException(String message, Throwable cause) {
        super(message, cause);
    }
}
