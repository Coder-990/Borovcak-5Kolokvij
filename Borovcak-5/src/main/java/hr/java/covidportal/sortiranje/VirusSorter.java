package main.java.hr.java.covidportal.sortiranje;

import main.java.hr.java.covidportal.model.Virus;

import java.util.Comparator;
import java.util.Locale;

public class VirusSorter implements Comparator<Virus> {

    @Override
    public int compare(Virus v1, Virus v2) {

        if(v1.getNaziv().toLowerCase(Locale.ROOT).compareTo(v2.getNaziv().toLowerCase(Locale.ROOT)) > 0 ){
            return -1;
        }else if(v1.getNaziv().toLowerCase(Locale.ROOT).compareTo(v2.getNaziv().toLowerCase(Locale.ROOT)) < 0){
            return 1;
        }else{
            return 0;
        }
    }
}
