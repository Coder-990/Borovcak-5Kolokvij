package main.java.hr.java.covidportal.iznimke;

public class DuplikatKontaktiraneOsobeException extends Exception {

    public DuplikatKontaktiraneOsobeException(String message) {
        super(message);
    }

    public DuplikatKontaktiraneOsobeException(Throwable cause) {
        super(cause);
    }

    public DuplikatKontaktiraneOsobeException(String message, Throwable cause) {
        super(message, cause);
    }
}
