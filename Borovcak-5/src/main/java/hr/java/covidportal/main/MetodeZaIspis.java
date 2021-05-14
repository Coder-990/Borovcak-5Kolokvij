package main.java.hr.java.covidportal.main;

import main.java.hr.java.covidportal.genericsi.KlinikaZaInfektivneBolesti;
import main.java.hr.java.covidportal.model.*;
import main.java.hr.java.covidportal.sortiranje.CovidSorter;
import main.java.hr.java.covidportal.sortiranje.VirusSorter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MetodeZaIspis {

    protected static void ispisMapomBolestiOsoba(Set<Zupanija> zupanije, Map<Bolest, List<Osoba>> bolestiOsobaMap) {
        for (Bolest bolestVirusOsobe : bolestiOsobaMap.keySet()) {
            if (bolestVirusOsobe instanceof Virus virusOsobe) {
                System.out.println("Od zarazenosti virusa " + virusOsobe.getNaziv().toLowerCase(Locale.ROOT) + " boluju: ");
                int i = 0;
                for (Osoba osobeZarazeneBolescu : bolestiOsobaMap.get(bolestVirusOsobe)) {
                    System.out.println((i += 1) + ". " + osobeZarazeneBolescu.getIme() + " " + osobeZarazeneBolescu.getPrezime());
                }
            } else {
                System.out.println("Od bolesti " + bolestVirusOsobe.getNaziv().toLowerCase(Locale.ROOT) + " boluju: ");
                int i = 0;
                for (Osoba osobeZarazeneBolescu : bolestiOsobaMap.get(bolestVirusOsobe)) {
                    System.out.println((i += 1) + ". " + osobeZarazeneBolescu.getIme() + " " + osobeZarazeneBolescu.getPrezime());
                }
            }
        }

        List<Zupanija> sortiraneZupanije = new ArrayList<>(zupanije);
        sortiraneZupanije.sort(new CovidSorter());
        double min = ((sortiraneZupanije.get(0).getBrojZarazenihOsoba() / sortiraneZupanije.get(0).getBrojStanovnikaZupanije()) * 100);
        double max = ((sortiraneZupanije.get(sortiraneZupanije.size() - 1).getBrojZarazenihOsoba() / sortiraneZupanije.get(sortiraneZupanije.size() - 1).getBrojStanovnikaZupanije()) * 100);
        System.out.print("Najmanji postotak zarazenih ima zupanija: " + sortiraneZupanije.get(0).getNaziv() + " sa postotkom zarazenosti: " + min + " %\n");
        System.out.print("Najveci postotak zarazenih ima zupanija: " + sortiraneZupanije.get(sortiraneZupanije.size() - 1).getNaziv() + " sa postotkom zarazenosti: " + max + " %\n");
    }

    protected static void sortiraniVirusiBezLambde(KlinikaZaInfektivneBolesti<Virus, Osoba> klinikaZaInfektivneBolesti) {
        List<Virus> bolestiSortiraneBezLambde = klinikaZaInfektivneBolesti.getListaSvihUnesenihVirusa();
        bolestiSortiraneBezLambde.sort(new VirusSorter());
        System.out.println("Sortirani virusi abecedom obrnuto: ");
        for (Virus vir:bolestiSortiraneBezLambde) {
            System.out.println(vir.getNaziv());
        }
    }

    protected static void sortiraniVirusiLambdom(KlinikaZaInfektivneBolesti<Virus, Osoba> klinikaZaInfektivneBolesti) {

        List<Virus> sortiraneBolesti = klinikaZaInfektivneBolesti.getListaSvihUnesenihVirusa();
        sortiraneBolesti.sort((o1, o2) -> o1.getNaziv().toLowerCase(Locale.ROOT).compareTo(o2.getNaziv().toLowerCase(Locale.ROOT)) * (-1));
        System.out.println("Sortirani virusi obrnuto od abecede lambda izrazom: ");
        sortiraneBolesti.forEach(virus -> System.out.println(virus.getNaziv()));

    }

    protected static Optional<List<Osoba>> pronadiOsobuPoPrezimenu(Scanner scan, List<Osoba> osobe){
        System.out.print("Unesite string za pretragu po prezimenu: ");
        String pretragaOsobePoPrezimenu = MetodeZaValidaciju.provjeraPraznogStringaBroja(scan);
        return Optional.of(osobe.stream()
                .filter(osoba -> osoba.getPrezime().toLowerCase(Locale.ROOT).contains(pretragaOsobePoPrezimenu.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList()));
    }
}
