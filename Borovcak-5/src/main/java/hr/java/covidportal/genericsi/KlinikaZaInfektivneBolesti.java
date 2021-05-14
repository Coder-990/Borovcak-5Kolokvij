package main.java.hr.java.covidportal.genericsi;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Virus;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
@ToString
public class KlinikaZaInfektivneBolesti <T extends Virus, S extends Osoba> {


    private List<T> listaSvihUnesenihVirusa;
    private List<S> listaSvihUnesenihOsobaZarezeneVirusom;

    public KlinikaZaInfektivneBolesti() {
        this.listaSvihUnesenihVirusa = new ArrayList<T>();
        this.listaSvihUnesenihOsobaZarezeneVirusom = new ArrayList<S>();
    }

    public void dodajBolestiZarazeneVirusom(T unesenaBolest){
        if (!listaSvihUnesenihVirusa.contains(unesenaBolest)){
            listaSvihUnesenihVirusa.add(unesenaBolest);
        }
    }

    public void dodajOsobuZarazenuVirusom(S unesenaOsobaZarazenaVirusom){
        if(!listaSvihUnesenihOsobaZarezeneVirusom.contains(unesenaOsobaZarazenaVirusom)){
            listaSvihUnesenihOsobaZarezeneVirusom.add(unesenaOsobaZarazenaVirusom);
        }
    }
}
