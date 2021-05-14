package main.java.hr.java.covidportal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

/**
 * Predstavlja model virus
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Virus extends Bolest implements Zarazno {
    /**
     * Nasljeduje klasu Bolest koja nasljeduje klasu ImenovaniEntitet, implementira sučelje Zarazno. Konstruktor klasa Virus služi za definiranje objeka virus, nasljeđuje dva parametra: naziv i polje objekata klase tipa Simptom, a prima naziv objkta klase tipa String i polje objeta klase tipa Simptom.
     *
     * @param naziv    String naziv virusa
     * @param simptomi Set Simptom set simptoma
     */
    public Virus(String naziv, Set<Simptom> simptomi) {
        super(naziv, simptomi);
    }

    /**
     * Ne vraća ništa, a prima objekt klase tipa Osoba. Služi za prelazak zaraze na tu osobu. Svaka klasa koja implementira sučelje zarazno, poziva tu metodu koja izvršava prelazak zaraze na osoba koja je bila s tom osobom u kontaktu.
     *
     * @param osoba Osoba objekt klase tipa Osoba.
     */
    public void prelazakZarazeNaOsobu(Osoba osoba) {

        osoba.setZarazenBolescu(this);
    }

    @Override
    public String toString() {
        return "Virus{" +
                "simptomi=" + simptomi +
                ", naziv='" + naziv + '\'' +
                '}';
    }
}
