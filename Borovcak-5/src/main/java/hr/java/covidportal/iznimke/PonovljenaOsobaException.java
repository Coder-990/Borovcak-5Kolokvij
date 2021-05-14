package main.java.hr.java.covidportal.iznimke;

/**
 * Created by domagoj on tra, 2021
 * Predstavlja oznacenu iznimku koja se baca kada se u izvrsavanju
 * programa unese osoba s istim imenom, prezimenom, starosti i zupanija prebivalista.
 */
public class PonovljenaOsobaException extends Exception {

    /**
     * Konstruktor koji prima objekt klase tipa String
     *
     * @param message String poruka iznimke
     */
    public PonovljenaOsobaException(String message) {
        super(message);
    }

    /**
     * Konstruktor koji prima objekt klase tipa Throwable
     *
     * @param cause Throwable uzrok iznimke
     */
    public PonovljenaOsobaException(Throwable cause) {
        super(cause);
    }

    /**
     * Konstruktor koji prima objekt klase tipa String i Throwable
     *
     * @param message String poruka iznimke
     * @param cause   Throwable uzrok iznimke
     */
    public PonovljenaOsobaException(String message, Throwable cause) {
        super(message, cause);
    }
}
