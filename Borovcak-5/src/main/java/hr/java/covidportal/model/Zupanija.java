package main.java.hr.java.covidportal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * Predstavlja model Županija
 */
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class Zupanija extends ImenovaniEntitet {

    Integer brojStanovnikaZupanije;
    Double brojZarazenihOsoba;

    /**
     * Nasljeduje klasu ImenovaniEntitet. Konstruktor klase Županija služi za definiranje objeka zupanija, nasljeđuje parametar: naziv, a prima naziv objkta klase tipa String, broj stanovnika po županiji objeta klase tipa Integer i broj zaraženih osoba po zupaniji objekta klase tipa Double.
     *
     * @param naziv                  String naziv županije
     * @param brojStanovnikaZupanije Double broj stanovnika po županiji
     * @param brojZarazenihOsoba     Double broj zarazenih osoba po zupaniji
     */
    public Zupanija(String naziv, Integer brojStanovnikaZupanije, Double brojZarazenihOsoba) {
        super(naziv);
        this.naziv = naziv;
        this.brojStanovnikaZupanije = brojStanovnikaZupanije;
        this.brojZarazenihOsoba = brojZarazenihOsoba;
    }

    @Override
    public String toString() {
        return "Zupanija{" +
                "naziv='" + naziv + '\'' +
                ", brojStanovnikaZupanije=" + brojStanovnikaZupanije +
                ", brojZarazenihOsoba=" + brojZarazenihOsoba +
                '}';
    }
}
