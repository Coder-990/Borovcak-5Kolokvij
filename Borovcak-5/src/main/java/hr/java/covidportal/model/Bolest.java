package main.java.hr.java.covidportal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * Predstavlja model bolest
 */
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class Bolest extends ImenovaniEntitet {

    Set<Simptom> simptomi;

    /**
     * Nasljeduje klasu ImenovaniEntitet. Konstruktor klase Bolest služi za definiranje objeka bolest, nasljeđuje parametar naziv objekata klase tipa String, a prima naziv objkta klase tipa String i set objeta klase tipa Simptom.
     *
     * @param naziv    String naziv bolesti
     * @param simptomi Set Simptom set simptoma
     */
    public Bolest(String naziv, Set<Simptom> simptomi) {
        super(naziv);
        this.naziv = naziv;
        this.simptomi = simptomi;
    }

    @Override
    public String toString() {
        return "Bolest{" +
                "simptomi=" + simptomi +
                ", naziv='" + naziv + '\'' +
                '}';
    }
}
