package main.java.hr.java.covidportal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import main.java.hr.java.covidportal.enumeracija.VrijednostiSimptoma;

/**
 * Predstavlja model simptom
 */
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class Simptom extends ImenovaniEntitet {

    //    NazivSimptoma nazivSimptoma;
    VrijednostiSimptoma vrijednostiSimptoma;

    /**
     * Nasljeduje klasu ImenovaniEntitet. Konstruktor klase Simptom služi za definiranje objeka simptom, nasljeđuje parametar naziv, a prima naziv objkta klase tipa String i vrijednostiSimptoma objkta klase enumeracija tipa VrijednostiSimptoma.
     *
     * @param naziv               String naziv bolesti
     * @param vrijednostiSimptoma Enumeracija predstavlja vrijednost simptoma
     */
    public Simptom(String naziv, VrijednostiSimptoma vrijednostiSimptoma) {
        super(naziv);
        this.naziv = naziv;
        this.vrijednostiSimptoma = vrijednostiSimptoma;
    }

    @Override
    public String toString() {
        return "Simptom{" +
                "naziv='" + naziv + '\'' +
                ", vrijednostiSimptoma=" + vrijednostiSimptoma +
                '}';
    }
}
