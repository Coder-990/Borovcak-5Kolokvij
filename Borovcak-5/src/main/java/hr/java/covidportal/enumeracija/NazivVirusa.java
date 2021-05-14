package main.java.hr.java.covidportal.enumeracija;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@AllArgsConstructor
public enum NazivVirusa {

    NEMA_VIRUSA, CORONA, EBOLA, MARBURG, BJESNOCA, HIV, INFLUENZA, ROTA;

    String nazivVirusa;

    public String getNazivVirusa() {
        return nazivVirusa;
    }

    public void setNazivVirusa(String nazivVirusa) {
        this.nazivVirusa = nazivVirusa;
    }
}
