package main.java.hr.java.covidportal.main;

import main.java.hr.java.covidportal.enumeracija.VrijednostiSimptoma;
import main.java.hr.java.covidportal.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static main.java.hr.java.covidportal.main.Glavna.*;
import static main.java.hr.java.covidportal.main.MetodeZaValidaciju.brojKontaktiranihOsoba;

/**
 * Klasa svih pomocnih metoda za odabir podataka koje se pozivaju prilikom izvrsavanja programa
 */
public class MetodeZaOdabir {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    private static String messageInfoError = "Pogreska, pokusali ste unijeti: ";
    private static String messageRepeatInput = ",molim ponovite Vas unos: ";

    /**
     * Služi za odabir objekta zupanije, prima objekt klase tipa Scaner i set objekta klase tipa Zupanija, a vraca objekt klase tipa Zupanija pod odabranim indeksom.
     *
     * @param scan     Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param zupanije Set Zupanije polje objekata služi za odabir zupanija.
     * @return Zupanija odaberen objekt zupanije pod određenim indeksom.
     */
    protected static Zupanija odabirZupanije(Scanner scan, Set<Zupanija> zupanije) {
        AtomicInteger index = new AtomicInteger();
        List<Zupanija> sveZupanije = new ArrayList<>(zupanije);
        System.out.println(sveZupanije.stream()
                .map(zup -> (index.addAndGet(1)) + ". " + zup.getNaziv())
                .collect(Collectors.joining("\n")));
        System.out.print("Odabir: ");
        int odabir = MetodeZaValidaciju.provjeraBrojaSaListe(scan, BROJ_ZUPANIJA);
        return sveZupanije.get(odabir - 1);
    }

    /**
     * Služi za odabir objekta simptomi, prima objekt klase tipa Scaner i set objekta klase tipa Simptom, a vraca objekt klase tipa Simptom pod odabranim indeksom.
     *
     * @param scan     Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param simptomi Set Simptom set objekata služi za odabir simptoma kod unosa bolesti.
     * @return Simptom odaberen objekt simptom pod određenim indeksom.
     */
    protected static Simptom odabirSimptoma(Scanner scan, Set<Simptom> simptomi) {
        AtomicInteger index = new AtomicInteger();
        List<Simptom> sviSimptomi = new ArrayList<>(simptomi);
        System.out.println(simptomi.stream()
                .map(simptom -> index.addAndGet(1) + ". " + simptom.getNaziv() + ":" + simptom.getVrijednostiSimptoma())
                .collect(Collectors.joining("\n")));

        System.out.print("Odabir: ");
        int odabir = MetodeZaValidaciju.provjeraBrojaSaListe(scan, BROJ_SIMPTOMA);
        return sviSimptomi.get(odabir - 1);
    }

    /**
     * Služi za odabir objekta bolesti, prima objekt klase tipa Scaner i set objekta klase tipa Bolest, a vraca objekt klase tipa Bolest pod odabranim indeksom.
     *
     * @param scan    Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param bolesti Set Bolest set objekata služi za odabir bolesti za svaku osobu.
     * @return Bolest odaberen objekt bolest pod određenim indeksom.
     */
    protected static Bolest odabirBolesti(Scanner scan, Set<Bolest> bolesti) {
        AtomicInteger index = new AtomicInteger();
        List<Bolest> sveBolesti = new ArrayList<>(bolesti);
        System.out.println(sveBolesti.stream()
                .map(bolest -> index.addAndGet(1) + ". " + bolest.getNaziv())
                .collect(Collectors.joining("\n")));
        System.out.print("Odabir: ");
        int odabir = MetodeZaValidaciju.provjeraBrojaSaListe(scan, BROJ_BOLESTI);
        return sveBolesti.get(odabir - 1);
    }

    /**
     * Služi za odabir objekta osobe, prima objekt klase tipa Scaner i lista objekta klase tipa Osoba, a vraca objekt klase tipa Osoba pod odabranim indeksom.
     *
     * @param scan  Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param osobe List Osobe lista objekata služi za odabir kontaktirane osobe za svaku osobu koja se tek odabire nakon prve unesene osobe.
     * @return Osoba odaberen objekt osoba pod određenim indeksom.
     */
    protected static Osoba odabirOsobe(Scanner scan, List<Osoba> osobe) {
        AtomicInteger index = new AtomicInteger();
        System.out.println(osobe.stream()
                .map(osoba -> index.addAndGet(1) + ". " + osoba.getIme() + " " + osoba.getPrezime()
                        + ",starosti " + osoba.getStarost() + " godina")
                .collect(Collectors.joining("\n")));
        System.out.print("Odabir: ");
        int odabir = MetodeZaValidaciju.provjeraBrojaSaListe(scan, brojKontaktiranihOsoba(osobe));
        return osobe.get(odabir - 1);
    }

    /**
     * Kod unosa služi za odabir tipa klase Bolest ili Virus koja prima objekt klase tipa Scanner, a vraca objekt klase tipa Integer.
     *
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice
     * @return Integer odabreni indeks bolesti ili virusa pod kojim se nalazi
     */
    protected static Integer odabirBolestiVirusa(Scanner scan) {

        int odabir;
        System.out.print("Unosite li bolest ili virus osobe?\n1) BOLEST\n2) VIRUS\nOdabir: ");
        do {
            odabir = MetodeZaValidaciju.IntegerExHandler(scan);
            if (odabir < 1 || odabir > 2) {
                logger.info(messageInfoError + " '" + odabir + "'");
                System.out.print("Niste odabrali ni bolest ni virus " + messageRepeatInput);
            }
        } while (odabir < 1 || odabir > 2);
        return odabir;
    }

    /**
     * Služi za odabir konstati kod svkog unosa simptoma odabere se vrijednost pojave simptoma
     *
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice
     * @return VrijednostiSimptoma vraca konstantu vrijednosti simptoma pod odabranim indeksom
     */
    protected static VrijednostiSimptoma odabirVrijednostiSimptoma(Scanner scan) {

        for (int i = 0; i < VrijednostiSimptoma.values().length; i++) {
            System.out.println((i + 1) + ". " + VrijednostiSimptoma.values()[i]);
        }
        int odabirVrijednostiSimptoma;
        System.out.print("Odabir: ");
        do {
            odabirVrijednostiSimptoma = MetodeZaValidaciju.IntegerExHandler(scan);
            if (odabirVrijednostiSimptoma < 0 || odabirVrijednostiSimptoma == 0 || odabirVrijednostiSimptoma > VrijednostiSimptoma.values().length) {
                System.err.println(messageInfoError + " '"+odabirVrijednostiSimptoma +"' " + messageRepeatInput);
                logger.warn(messageInfoError + " '"+odabirVrijednostiSimptoma +"'");
            }
        } while (odabirVrijednostiSimptoma < 0 || odabirVrijednostiSimptoma == 0 || odabirVrijednostiSimptoma > VrijednostiSimptoma.values().length);

        return VrijednostiSimptoma.values()[odabirVrijednostiSimptoma - 1];
    }
}

  /*
    /**
     * Kod unosa bolesti služi za unos između tri vrijednosti klase tipa Simptom, prima objekt klase tipa Scanner, a vraca objekt klase tipa String.
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @return String unesena jedna od tri vrijednosti klase tipa Simptom.

    protected static String odabirVrijednostiBolesti(Scanner scan) {

        do {
            String inputString = scan.nextLine().toUpperCase();
            if ("RIJETKO".equals(inputString) || "SREDNJE".equals(inputString) || "CESTO".equals(inputString)) {
                return inputString;
            } else {
                logger.info(messageInfoError + " '" + inputString + "' ");
                System.out.print("Unesite jedan od ponudenih odgovora (RIJETKO, SREDNJE ILI CESTO), ponovite unos: ");
            }
        } while (true);
    }

    protected static NazivSimptoma odabirSimptoma(Scanner scan){

        for (int i = 0; i < NazivSimptoma.values().length; i++){
            System.out.println((i += 1) + ". " + NazivSimptoma.values()[i]);
        }
        Integer odabirNazivaSimptoma;
        do {
            System.out.print("Odabir: ");
            odabirNazivaSimptoma = MetodeZaValidaciju.IntegerExHandler(scan);
        }while (odabirNazivaSimptoma <0 || odabirNazivaSimptoma == 0 || odabirNazivaSimptoma > NazivSimptoma.values().length);

        return NazivSimptoma.values()[odabirNazivaSimptoma-1];
    }

    protected static NazivBolesti odabirNazivaBolesti(Scanner scan){

        for (int i = 0; i < NazivBolesti.values().length; i++){
            System.out.println((i += 1) + ". " + NazivBolesti.values()[i]);
        }
        Integer odabirNazivaBolesti;
        do {
            System.out.print("Odabir: ");
            odabirNazivaBolesti = MetodeZaValidaciju.IntegerExHandler(scan);
        }while (odabirNazivaBolesti <0 || odabirNazivaBolesti == 0 || odabirNazivaBolesti > NazivBolesti.values().length);

        return NazivBolesti.values()[odabirNazivaBolesti-1];
    }

    protected static NazivVirusa odabirNazivaVirusa(Scanner scan){

        for (int i = 0; i < NazivVirusa.values().length; i++){
            System.out.println((i += 1) + ". " + NazivVirusa.values()[i]);
        }
        Integer odabirNazivaVirusa;
        do {
            System.out.print("Odabir: ");
            odabirNazivaVirusa = MetodeZaValidaciju.IntegerExHandler(scan);
        }while (odabirNazivaVirusa <0 || odabirNazivaVirusa == 0 || odabirNazivaVirusa > NazivBolesti.values().length);

        return NazivVirusa.values()[odabirNazivaVirusa-1];
    }

 */

