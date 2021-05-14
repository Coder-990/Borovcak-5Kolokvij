package main.java.hr.java.covidportal.main;

import main.java.hr.java.covidportal.genericsi.KlinikaZaInfektivneBolesti;
import main.java.hr.java.covidportal.genericsi.SviImenovaniEntiteti;
import main.java.hr.java.covidportal.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static main.java.hr.java.covidportal.main.MetodeZaIspis.*;
import static main.java.hr.java.covidportal.main.MetodeZaValidaciju.*;

/**
 * Glavna klasa za, unos podataka i ispis podataka.
 */
public class Glavna {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    protected static int BROJ_ZUPANIJA;
    protected static int BROJ_SIMPTOMA;
    protected static int BROJ_BOLESTI;
    protected static int BROJ_OSOBA;

    /**
     * Glavna metoda za izvršavanje programa, u kojoj se radi unos podataka i pozivaju
     * dodatne druge metode.
     *
     * @param args ulazni parametri kod pokretanja glavne metode
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        KlinikaZaInfektivneBolesti<Virus, Osoba> klinikaZaInfektivneBolesti = new KlinikaZaInfektivneBolesti<>();

        /**
         * PETI-3
         */
        SviImenovaniEntiteti<ImenovaniEntitet> nazivEntiteta = new SviImenovaniEntiteti<>();
        
        Set<Zupanija> zupanije = new HashSet<>();
        Set<Simptom> simptomi = new HashSet<>();
        Set<Bolest> bolesti = new HashSet<>();
        List<Osoba> osobe = new ArrayList<>();

        Map<Bolest, List<Osoba>> bolestiOsobaMap = new HashMap<>();

        forSviUnosa(scan,nazivEntiteta, klinikaZaInfektivneBolesti, zupanije, simptomi, bolesti, osobe, bolestiOsobaMap);

        System.out.println("Popis osoba: ");
        for (Osoba osoba : osobe) {
            System.out.println(osoba.toString());
        }

        ispisMapomBolestiOsoba(zupanije, bolestiOsobaMap);

        Instant startTimeLambda = Instant.now();
        sortiraniVirusiLambdom(klinikaZaInfektivneBolesti);
        Instant stopTimeLambda = Instant.now();
        System.out.println("Vrijeme sortiranja lambdom : " + Duration.between(startTimeLambda, stopTimeLambda).toNanos() + " nanos");

        Instant startTime = Instant.now();
        sortiraniVirusiBezLambde(klinikaZaInfektivneBolesti);
        Instant stopTime = Instant.now();
        System.out.println("Sortiranje bez lambdi : " + Duration.between(startTime, stopTime).toNanos() + " nanos");

        Optional<List<Osoba>> ispisPronadenihOsobaPoPrezimenu = pronadiOsobuPoPrezimenu(scan, osobe);
        if (ispisPronadenihOsobaPoPrezimenu.isPresent() && !ispisPronadenihOsobaPoPrezimenu.get().isEmpty()) {
            ispisPronadenihOsobaPoPrezimenu.get().forEach(System.out::println);
        } else {
            System.out.println("Nema pronadenih osoba");
        }

        System.out.println("Broj simptoma bolesti: ");
        List<String> brojSimptomabolesti = bolesti.stream().map(bolest -> bolest.getNaziv() + ":" + bolest.getSimptomi().size()).collect(Collectors.toList());
        brojSimptomabolesti.forEach(System.out::println);

        /**
         * PETI-3
         */
        System.out.println("Test Zadnji zadatak");
        List<ImenovaniEntitet> sortiranjePremaTipu = nazivEntiteta.getListaSvihImenovanihEntiteta();
        sortiranjePremaTipu.stream().allMatch(tip -> tip.getClass().isInstance(nazivEntiteta));
        System.out.println("Podaci prema tipu : ");
        sortiranjePremaTipu.stream().map(ImenovaniEntitet::toString).forEach(System.out::println);
        System.out.println();

        /**
         * PETI-2
         */
        List<Zupanija> sveZupanije = new ArrayList<>(zupanije);
        DoubleSummaryStatistics doubleSummaryStatisticsZarazeniStanovnici = sveZupanije.stream()
                .mapToDouble(Zupanija::getBrojZarazenihOsoba)
                .filter(rating -> rating > 0.0D)
                .summaryStatistics();
        double prebrojiZarazene = doubleSummaryStatisticsZarazeniStanovnici.getSum();
        double prosjecanBrojZarazenih = doubleSummaryStatisticsZarazeniStanovnici.getAverage();

        List<Zupanija> sveNadprosjecne = sveZupanije.stream()
                .filter(zupanija -> zupanija.getBrojZarazenihOsoba().compareTo(prosjecanBrojZarazenih) > 0)
                .collect(Collectors.toList());
        System.out.println("Sve nadprosjecne zupanije :");
        System.out.println(sveNadprosjecne.stream().
                map(zupanija ->"Zupanija: "+ zupanija.getNaziv()
                + " broj stanovnika:" + zupanija.getBrojStanovnikaZupanije() + " brojZarazenih: "
                        + zupanija.getBrojZarazenihOsoba()).collect(Collectors.joining("\n")));



        System.out.println();
        /**
         * PETI-1
         */
        DoubleSummaryStatistics doubleSummaryStatisticsSviStanovnici = sveZupanije.stream()
                .mapToDouble(Zupanija::getBrojStanovnikaZupanije)
                .summaryStatistics();
        double prebrojiSveStanovnike = doubleSummaryStatisticsSviStanovnici.getSum();
        double postotakZarazenihSvihZupanija = (prebrojiZarazene / prebrojiSveStanovnike) * 100;
        System.out.println("Postotak zarazenih po svim zupanijama zajedno je : " + postotakZarazenihSvihZupanija + "%");

        scan.close();
    }

    private static void forSviUnosa(Scanner scan, SviImenovaniEntiteti<ImenovaniEntitet> nazivEntiteta, KlinikaZaInfektivneBolesti<Virus, Osoba> klinikaZaInfektivneBolesti, Set<Zupanija> zupanije, Set<Simptom> simptomi, Set<Bolest> bolesti, List<Osoba> osobe, Map<Bolest, List<Osoba>> bolestiOsobaMap) {

        System.out.print("Unesite broj zupanija: ");
        BROJ_ZUPANIJA = IntegerExHandler(scan);
        System.out.println("Unesite podatke o " + BROJ_ZUPANIJA + " zupanija!");
        for (int i = 0; i < BROJ_ZUPANIJA; i++) {
            Zupanija zupanija = MetodeZaUnosPodataka.unosZupanije(scan, i);
            nazivEntiteta.dodajImenovaniEntitet(zupanija);
            zupanije.add(zupanija);
        }


        System.out.print("Unesite broj simptoma: ");
        BROJ_SIMPTOMA = IntegerExHandler(scan);
        System.out.println("Unesite podatke o " + BROJ_SIMPTOMA + " simptoma!");
        for (int i = 0; i < BROJ_SIMPTOMA; i++) {
            Simptom simptom =MetodeZaUnosPodataka.unosSimptoma(scan, i);
            nazivEntiteta.dodajImenovaniEntitet(simptom);
            simptomi.add(simptom);
        }

        System.out.print("Unesite broj bolesti: ");
        BROJ_BOLESTI = IntegerExHandler(scan);
        System.out.println("Unesite podatke o " + BROJ_BOLESTI + " bolesti!");
        for (int i = 0; i < BROJ_BOLESTI; i++) {
            hvatanjeNeoznaceneIznimkeBolestIstihSimptoma(scan,nazivEntiteta, simptomi, bolesti, klinikaZaInfektivneBolesti, i);
        }

        System.out.print("Unesite broj osoba: ");
        BROJ_OSOBA = IntegerExHandler(scan);
        System.out.println("Unesite podatke o " + BROJ_OSOBA + " osobe!");
        for (int i = 0; i < BROJ_OSOBA; i++) {
            hvatanjeOznaceneIznimkePonovljenaOsobaKodUnosa(scan, zupanije, bolesti, osobe, bolestiOsobaMap, klinikaZaInfektivneBolesti, i);
        }
    }
}
