package main.java.hr.java.covidportal.enumeracija;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public enum NazivBolesti {

    NEMA_BOLESTI, AIDS, ANEMIJA, ANOREKSIJA, BRONHITIS, GASTRITIS, DIJABETES, EPILEPSIJA, LUPUS, RAK;

    String nazivBolesti;

    public String getNazivBolesti() {
        return nazivBolesti;
    }

}
