package main.java.hr.java.covidportal.main;

import main.java.hr.java.covidportal.enumeracija.VrijednostiSimptoma;
import main.java.hr.java.covidportal.genericsi.KlinikaZaInfektivneBolesti;
import main.java.hr.java.covidportal.genericsi.SviImenovaniEntiteti;
import main.java.hr.java.covidportal.iznimke.DuplikatKontaktiraneOsobeException;
import main.java.hr.java.covidportal.model.*;

import java.util.*;

import static main.java.hr.java.covidportal.main.MetodeZaOdabir.odabirSimptoma;
import static main.java.hr.java.covidportal.main.MetodeZaValidaciju.hvatanjeOznaceneIznimkeDuplikataOsobe;
import static main.java.hr.java.covidportal.main.MetodeZaValidaciju.provjeraBrojaSaListe;

/**
 * Klasa svih pomocnih metoda za unos podataka koje se pozivaju prilikom izvrsavanja programa
 */
public class MetodeZaUnosPodataka {
    /**
     * Služi za stvaranje objekta zupanije, prima objekt klase tipa Scaner i Integer, a vraca objekt klase tipa Zupanija
     *
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice
     * @param i    Integer cjelobrojna vrijednost oznacava redBr zupanije
     * @return Zupanija stvoren novi objekt zupanije
     */
    protected static Zupanija unosZupanije(Scanner scan, Integer i) {

        System.out.print("Unesite naziv " + (i + 1) + ". zupanije: ");
        String nazivZupanije = MetodeZaValidaciju.provjeraPraznogStringaBroja(scan);
        System.out.print("Unesite broj stanovnika za '" + nazivZupanije + "' zupaniju: ");
        Integer brojStanovnika = MetodeZaValidaciju.IntegerExHandler(scan);
        System.out.print("Koliko je broj zarazenih osoba za '" + nazivZupanije + "' zupaniju: ");
        Double brojZarazenih = Double.valueOf(provjeraBrojaSaListe(scan, brojStanovnika));

        return new Zupanija(nazivZupanije, brojStanovnika, brojZarazenih);
    }

    /**
     * Služi za stvaranje objekta simptoma, prima objekt klase tipa Scaner i Integer, a vraca objekt klase tipa Simptom
     *
     * @param scan Scanner objek za slanje podataka u racunalo preko tipkovnice
     * @param i    Integer cjelobrojna vrijednost oznacava redBr simptoma
     * @return Simptom stvoren novi objekt simptoma
     */
    protected static Simptom unosSimptoma(Scanner scan, Integer i) {

        System.out.print("Unesite naziv " + (i + 1) + ". simptoma: ");
        String nazivSimptoma = MetodeZaValidaciju.provjeraPraznogStringaBroja(scan);
        System.out.println("Odaberite vrijednost simptoma '" + nazivSimptoma + "' (RIJETKO, SREDNJE, CESTO)!");
        VrijednostiSimptoma vrijednostSimptoma = MetodeZaOdabir.odabirVrijednostiSimptoma(scan);

        return new Simptom(nazivSimptoma, vrijednostSimptoma);
    }

    /**
     * Služi za stvaranje objekta bolesti, prima objekt klase tipa Scaner, set objekata klase tipa Simptom i objekt klase tipa Integer, a vraca objekt klase tipa Bolest
     *
     * @param scan     Scanner objekt za slanje podataka u racunalo preko tipkovnice
     * @param simptomi Set Simptom set objekata služi za unos simptoma za svaku bolest
     * @param i        Integer cjelobrojna vrijednost oznacava redBr bolesti
     * @return Bolest stvoren novi objekt bolesti
     */
    protected static Bolest unosBolesti(Scanner scan, Set<Simptom> simptomi, Integer i) {

        Integer odabirVirusaBolesti = MetodeZaOdabir.odabirBolestiVirusa(scan);
        System.out.print("Unesite naziv " + (i + 1) + ". bolesti ili virusa: ");
        String nazivBolesti = MetodeZaValidaciju.provjeraPraznogStringaBroja(scan);
        System.out.print("Unesite broj simptoma: ");
        Integer brojSimptoma = MetodeZaValidaciju.IntegerExHandler(scan);
        Set<Simptom> simptomBolesti = new HashSet<>();
        for (int j = 0; j < brojSimptoma; j++) {
            System.out.println("Odaberite " + (j + 1) + ". simptom: ");
            simptomBolesti.add(odabirSimptoma(scan, simptomi));
        }
        if (odabirVirusaBolesti == 1) {
            return new Bolest(nazivBolesti, simptomBolesti);
        } else {
            return new Virus(nazivBolesti, simptomBolesti);
        }
    }

    /**
     * Služi za stvaranje objekta osoba, prima objekt klase tipa Scaner, set objekata klase tipa:Zupanija, Bolest listu objekata klase tipa Osoba, objekt klase tipa Integer, a vraca objekt klase tipa Osoba
     *
     * @param scan     Scanner objekt za slanje podataka u racunalo preko tipkovnice
     * @param zupanije Set Zupanije set objekata služi za odabir zupanija za svaku osobu
     * @param bolesti  Set Bolest set objekata služi za unos bolesti za svaku osobu
     * @param osobe    List Osobe lista objekata služi za unos kontaktirane osobe za svaku osobu
     * @param i        Integer cjelobrojna vrijednost oznacava redBr osobe
     * @return Osoba stvoren novi objekt osobe
     * @throws DuplikatKontaktiraneOsobeException nova označena iznimka koja se baca svaki put kada se odabere dva puta ista soba
     */
    protected static Osoba unosOsobe(Scanner scan, Set<Zupanija> zupanije, Set<Bolest> bolesti, List<Osoba> osobe,KlinikaZaInfektivneBolesti<Virus, Osoba> klinikaZaInfektivneBolesti, Integer i) throws DuplikatKontaktiraneOsobeException {

        System.out.print("Unesite ime " + (i + 1) + ". osobe: ");
        String imeOsobe = MetodeZaValidaciju.provjeraPraznogStringaBroja(scan);
        System.out.print("Unesite prezime " + (i + 1) + ". osobe: ");
        String prezimeOsobe = MetodeZaValidaciju.provjeraPraznogStringaBroja(scan);
        System.out.print("Unesite starost " + (i + 1) + ". osobe: ");
        Integer starostOsobe = MetodeZaValidaciju.IntegerExHandler(scan);
        System.out.println("Odaberite zupaniju prebivalista " + (i + 1) + " osobe: ");
        Zupanija prebivalisteOsobe = MetodeZaOdabir.odabirZupanije(scan, zupanije);
        System.out.println("Odaberite bolest/virus " + (i + 1) + " osobe : ");
        Bolest bolestOsobe = MetodeZaOdabir.odabirBolesti(scan, bolesti);
        Osoba osoba = new Osoba.Builder()
                .ime(imeOsobe)
                .prezime(prezimeOsobe)
                .starost(starostOsobe)
                .zupanija(prebivalisteOsobe)
                .zarazenBolescu(bolestOsobe)
                .build();
        if (i > 0) {
            return unosKontaktiraneOsobe(scan, osobe, osoba, klinikaZaInfektivneBolesti, i);
        }
        return osoba;
    }

    /**
     * Služi za stvaranje objekta osoba, prima objekt klase tipa Scaner, listu objekata klase tipa Osoba, objekt klase tipa Osoba i Integer, a vraca objekt klase tipa Osoba
     *
     * @param scan  Scanner objekt za slanje podataka u racunalo preko tipkovnice
     * @param osobe List Osobe set objekata služi za unos kontaktirane osobe za svaku osobu koja se tek odabire nakon prve unesene osobe
     * @param osoba Osobe objekt prima podatke o osobi kod stvaranja kontaktirane osobe
     * @param i     Integer cjelobrojna vrijednost oznacava redBr osobe
     * @return Osoba stvoren novi objekt osobe
     */
    protected static Osoba unosKontaktiraneOsobe(Scanner scan, List<Osoba> osobe, Osoba osoba, KlinikaZaInfektivneBolesti<Virus, Osoba> klinikaZaInfektivneBolesti, Integer i) {

        System.out.print("Unesite broj osoba koje su bile u kontaktu s tom osobom: ");
        Integer brojKontaktiranihOsoba = MetodeZaValidaciju.provjeraBrojaKontaktiranih(scan, i);
        List<Osoba> kontaktiraneOsobe = new ArrayList<>();
        System.out.println("Odaberite osobe koje su bile u kontaktu  s tom osobom :");
        for (int j = 0; j < brojKontaktiranihOsoba; j++) {
            hvatanjeOznaceneIznimkeDuplikataOsobe(scan, osobe, kontaktiraneOsobe);
        }
      Osoba zarazenaOsoba = new Osoba.Builder()
                .ime(osoba.getIme())
                .prezime(osoba.getPrezime())
                .starost(osoba.getStarost())
                .zupanija(osoba.getZupanija())
                .zarazenBolescu(osoba.getZarazenBolescu())
                .kontaktiraneOsobe(kontaktiraneOsobe)
                .build();

        if(osoba.getZarazenBolescu() instanceof Virus || zarazenaOsoba.getZarazenBolescu() instanceof Virus){
            klinikaZaInfektivneBolesti.dodajOsobuZarazenuVirusom(zarazenaOsoba);
            klinikaZaInfektivneBolesti.dodajOsobuZarazenuVirusom(osoba);
        }
        return zarazenaOsoba;
    }
}
