package main.java.hr.java.covidportal.model;

/**
 * Predstavlja sučelje zarazno
 */
public interface Zarazno {
    /**
     * Ne vraća ništa, a prima objekt klase tipa Osoba. Služi za prelazak zaraze na tu osobu. Svaka klasa koja implementira sučelje zarazno, poziva tu metodu koja izvršava prelazak zaraze na osoba koja je bila s tom osobom u kontaktu.
     *
     * @param osoba Osoba objekt klase tipa Osoba.
     */
    void prelazakZarazeNaOsobu(Osoba osoba);
}
