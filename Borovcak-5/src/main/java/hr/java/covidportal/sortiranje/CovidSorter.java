package main.java.hr.java.covidportal.sortiranje;

import main.java.hr.java.covidportal.model.Zupanija;

import java.util.Comparator;

/**
 * Created by domag on tra, 2021
 */
public class CovidSorter implements Comparator<Zupanija> {
    @Override
    public int compare(Zupanija z1, Zupanija z2) {
        double izracunPostotkaZarazenostiZ1 = (z1.getBrojZarazenihOsoba() / z1.getBrojStanovnikaZupanije()) * 100;
        double izracunPostotkaZarazenostiZ2 = (z2.getBrojZarazenihOsoba() / z2.getBrojStanovnikaZupanije()) * 100;

        return Double.compare(izracunPostotkaZarazenostiZ1, izracunPostotkaZarazenostiZ2);
    }
}
