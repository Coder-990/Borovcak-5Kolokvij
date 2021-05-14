package main.java.hr.java.covidportal.enumeracija;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


@AllArgsConstructor
@RequiredArgsConstructor
public enum NazivSimptoma {

    BEZ_SIMPTOMA, CURENJE_NOSA, CRVENILO, OSIP, SVRAB, PECKANJE_KOZE, VISOKA_TEMPERATURA, SUHO_GRLO, GUBITAK_NJUHA, GUBITAK_OKUSA, GLAVOBOLJA, BOL_U_LEDIMA,
    BOL_U_MISICIMA, POVRACANJE, PROLJEV, UCESTALO_MOKRENJE, ZATVOR;

    String nazivSimptoma;

    public String getNazivSimptoma() {
        return nazivSimptoma;
    }
}
