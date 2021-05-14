package main.java.hr.java.covidportal.main;

import main.java.hr.java.covidportal.enumeracija.NazivSimptoma;
import main.java.hr.java.covidportal.genericsi.KlinikaZaInfektivneBolesti;
import main.java.hr.java.covidportal.genericsi.SviImenovaniEntiteti;
import main.java.hr.java.covidportal.iznimke.BolestIstihSimptomaException;
import main.java.hr.java.covidportal.iznimke.DuplikatKontaktiraneOsobeException;
import main.java.hr.java.covidportal.iznimke.PonovljenaOsobaException;
import main.java.hr.java.covidportal.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Klasa svih pomocnih metoda za validaciju kod unosa i odabir podataka koje se pozivaju prilikom izvrsavanja programa
 */
public class MetodeZaValidaciju {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    private static String messageRepeatInputEmptyString = "Pogreska, pokusali ste unijeti prazan string: ";
    private static String messageRepeatNumberFormatEx = "Pogreska, pokusali ste unijeti: ";
    private static String messageRepeatInput = ", molim ponovite Vas unos: ";

    /**
     * Kod unosa stringova služi za provjeru koja se izvršava nad unesenim podatkom da li je string prazan, broj ili sadrži razmake, prima objekt klase tipa Scanner, a vraca objekt klase tipa String.
     *
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @return String unesena validirana vrijednost klase tipa String.
     */
    protected static String provjeraPraznogStringaBroja(Scanner scan) {

        String inputString;
        String regex = "-?\\d+(\\.\\d+)?";
        Pattern pattern = Pattern.compile(regex);
        do {
            inputString = scan.nextLine().trim();
            if (pattern.matcher(inputString).matches()) {
                logger.error(messageRepeatNumberFormatEx + " '" + inputString + "' ");
                System.err.print(messageRepeatNumberFormatEx + " '" + inputString + "' " + messageRepeatInput);
            } else if (inputString.isBlank() || inputString.isEmpty()) {
                logger.error(messageRepeatInputEmptyString + " '" + inputString + "' ");
                System.err.print(messageRepeatInputEmptyString + " '" + inputString + "' " + messageRepeatInput);
            }
        } while (inputString.isBlank() || pattern.matcher(inputString).matches());
        return inputString;
    }

    /**
     * Kod unosa cjelobrojnih vrijednosti provjerava da li je unesena vrijednost negativan broj i slovo, znak ili bilo koji drugi tip podatka koji ne može pretvoriti u broj, prima objekt klase tipa Scanner, a vraca objekt klase tipa Integer.
     *
     * @param scan Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @return Integer unesena validirana vrijednost klase tipa Integer.
     */
    protected static Integer IntegerExHandler(Scanner scan) {

        int inputInt = 0;
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        boolean vrtiPetlju;
        do {
            vrtiPetlju = false;
            try {
                inputInt = scan.nextInt();
                scan.nextLine();
                if (inputInt < 0) {
                    logger.error(messageRepeatNumberFormatEx + " '" + inputInt + "' " + messageRepeatInput);
                    System.err.print(messageRepeatNumberFormatEx + " '" + inputInt + "' " + messageRepeatInput);
                    vrtiPetlju = true;
                } else if (pattern.matcher(String.valueOf(inputInt)).matches()) {
                    return inputInt;
                }
            } catch (InputMismatchException ex) {
                logger.error(messageRepeatNumberFormatEx + " '" + inputInt + "' ", ex);
                System.err.print(messageRepeatNumberFormatEx + " '" + inputInt + "' " + messageRepeatInput);
                vrtiPetlju = true;
                scan.nextLine();
            }
        } while (vrtiPetlju);
        return inputInt;
    }

    /**
     * Služi za provjeru nad objektima klase tipa Bolest da li objekti imaju iste simptomske vrijednosti, a drugaciji naziv bolesti, prima objekt klase tipa Bolest, a ne vraća ništa.
     *
     * @param bolestA Bolest provjera jednakosti jednog objekta tipa Bolest nad drugim tipa Bolest
     * @param bolestB Bolest provjera jednakosti jednog objekta tipa Bolest nad drugim tipa Bolest
     */
    private static void istiSimptom(Bolest bolestA, Bolest bolestB) {

        Set<Simptom> simptomiA = bolestA.getSimptomi();
        Set<Simptom> simptomiB = bolestB.getSimptomi();
        int brojac = 0;
        if (simptomiA.size() == simptomiB.size()) {
            for (Simptom simA : simptomiA) {
                for (Simptom simB : simptomiB) {
                    if (simA.getVrijednostiSimptoma().equals(simB.getVrijednostiSimptoma())) {
                        brojac++;
                    }
                }
            }
            if (brojac == simptomiA.size()) {
                logger.error("Pogreska, dogodila se nova neoznacena iznimka, odabrana je bolest/virus sa istim simptomima!");
                throw new BolestIstihSimptomaException("Pogreska, odabrana je bolest/virus sa istim simptomima! '" + bolestA.getNaziv() + "' - '" + bolestA.getSimptomi() + "'\n'" + bolestB.getNaziv() + "' - '" + bolestB.getSimptomi() + "'");
            }
        }
    }

    /**
     * Služi za provjeru nad objektima klase tipa Bolest da li objekti imaju iste simptomske vrijednosti, a drugaciji naziv bolesti, prima objekt klase tipa Bolest i set objekata klase tipa Bolest, a ne vraća ništa.
     *
     * @param bolest               Bolest provjera jednakosti simptoma jednog objekta tipa Bolest nad poljem objekata klase tipa Bolest.
     * @param bolestiIstihSimptoma Set Bolest prosljeđivanje svih bolesti sa kojima se uspoređuje da li postoji bolest sa istim simptomima.
     */
    private static void provjeraBolestiSaIstimSimptomima(Bolest bolest, Set<Bolest> bolestiIstihSimptoma) {

        for (Bolest b : bolestiIstihSimptoma) {
            if (Optional.ofNullable(b).isPresent() && Optional.ofNullable(bolest).isPresent()) {
                istiSimptom(b, bolest);
            }
        }
    }

    /**
     * Služi za provjeru nad objektima klase tipa Osoba da li unesena ista osobe vise puta gdje se onda baca nova oznadena iznimka 'PonovljenaOsobaException', prima objekt klase tipa Osoba i listu objekata klase tipa Osoba, a ne vraća ništa.
     *
     * @param osoba Osoba provjera jednakosti jednog objekta tipa Osoba nad poljem objekata klase tipa Osoba.
     * @param osobe List Osoba lista svih osoba sa kojima se uspoređuje da li postoji dva puta isto odabrana osoba.
     * @throws PonovljenaOsobaException bacanje iznimke nakon sto se unese ista osoba
     */
    private static void provjeraPonovljeneOsobe(Osoba osoba, List<Osoba> osobe) throws PonovljenaOsobaException {

        for (Osoba o : osobe) {
            if (Optional.ofNullable(o).isPresent() && Optional.ofNullable(osoba).isPresent() && o.getIme().equals(osoba.getIme()) &&
                    o.getPrezime().equals(osoba.getPrezime()) && o.getStarost().equals(osoba.getStarost()) && o.getZupanija().equals(osoba.getZupanija())) {
                logger.error("Pogreska, dogodila se nova oznacena iznimka, odabrana je osoba sa istim imenom, prezimenom, starosti i zupanijom prebivalista!" + osoba);
                throw new PonovljenaOsobaException("Pogreska, dogodila se nova oznacena iznimka, odabrana je osoba sa istim imenom, prezimenom, starosti i zupanijom prebivalista!'" + osoba.getIme() + " " + osoba.getPrezime() + "'");
            }
        }
    }

    /**
     * Služi za hvatanje označene iznimke 'PonovljenaOsobaException' nad objektom klase tipa Osoba da li je unesena ista osobe vise puta gdje se onda hvata i obrađuje nova oznacena iznimka, nakon 3 puta isto unesene osobe program se prekida, prima objekt klase tipa Scanner, polje objekata klase tipa Zupanija, Bolesti i Osoba i objekt klase tipa Integer, a ne vraća ništa.
     *
     * @param scan     Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param zupanije Set Zupanije set objekata služi za odabir zupanija za svaku osobu
     * @param bolesti  Set Bolest set objekata služi za unos bolesti za svaku osobu
     * @param osobe    Set Osoba set objekata služi za provjeru unosa nad osobom za svaku osobu koja se unosi, dal je ta osoba unesena.
     * @param i        Integer indeks elementa od objekta osobe.
     */
    protected static void hvatanjeOznaceneIznimkePonovljenaOsobaKodUnosa(Scanner scan, Set<Zupanija> zupanije, Set<Bolest> bolesti, List<Osoba> osobe, Map<Bolest, List<Osoba>> bolestiOsobaMap, KlinikaZaInfektivneBolesti<Virus, Osoba> klinikaZaInfektivneBolesti, int i) {
        boolean vrtiPetlju = false;
        int brojacEx = 0;
        do {
            vrtiPetlju = false;
            try {
                Osoba osoba = MetodeZaUnosPodataka.unosOsobe(scan, zupanije, bolesti, osobe, klinikaZaInfektivneBolesti, i);
                provjeraPonovljeneOsobe(osoba, osobe);
                osobe.add(osoba);
                if (osoba.getZarazenBolescu() instanceof Virus) {
                    klinikaZaInfektivneBolesti.dodajOsobuZarazenuVirusom(osoba);
                }
                if (!bolestiOsobaMap.containsKey(osoba.getZarazenBolescu())) {
                    bolestiOsobaMap.put(osoba.getZarazenBolescu(), new ArrayList<>());
                }
                bolestiOsobaMap.get(osoba.getZarazenBolescu()).add(osoba);
            } catch (DuplikatKontaktiraneOsobeException ex) {
                logger.error("Pogreska, dogodila se nova oznacena iznimka, odabrana je ista osoba ", ex);
                System.out.println("Pogreska, dogodila se nova oznacena iznimka, odabrana je ista osoba!");
            } catch (PonovljenaOsobaException ex) {
                vrtiPetlju = true;
                brojacEx++;
                logger.error("Pogreska, dogodila se nova oznacena iznimka, unesena je osoba sa istim imenom, prezimenom, starosti i zupanijom prebivalista!", ex);
                System.out.println("Pogreska, dogodila se nova oznacena iznimka, odabrana je osoba sa istim imenom, prezimenom, starosti i zupanijom prebivalista" + " '" + brojacEx + "' " + "puta");
                if (brojacEx == 3) {
                    System.out.println("Ponovili ste unos osobe " + (brojacEx) + " puta, program zavrsava sa radom.");
                    logger.error("Prekinut je rad programa zbog 3 puta unesene iste osobe");
                    System.exit(0);
                }
            }
        } while (vrtiPetlju);
    }

    /**
     * Služi za provjeru nad objektima klase tipa Osoba da li objekti imaju iste odabrane osobe dva puta gdje se onda baca nova oznadena iznimka 'DuplikatKontaktiraneOsobeException', prima objekt klase tipa Osoba i listu objekata klase tipa Osoba, a ne vraća ništa.
     *
     * @param osoba Osoba provjera jednakosti jednog objekta tipa Osoba nad poljem objekata klase tipa Osoba.
     * @param osobe List Osoba lista svih osoba sa kojima se uspoređuje da li postoji dva puta isto odabrana osoba.
     */
    private static void provjeraDuplikataKontaktiranihOsoba(Osoba osoba, List<Osoba> osobe) throws DuplikatKontaktiraneOsobeException {

        for (Osoba o : osobe) {
            if (Optional.ofNullable(o).isPresent() && Optional.ofNullable(osoba).isPresent() && o.equals(osoba)) {
                logger.error("Pogreska, dogodila se nova oznacena iznimka, odabrana je ista osoba vise puta!");
                throw new DuplikatKontaktiraneOsobeException("Pogreska, odabrana je ista osoba vise puta '" + osoba.getIme() + " " + osoba.getPrezime() + "'");
            }
        }
    }

    /**
     * Služi za hvatanje označene iznimke 'DuplikatKontaktiraneOsobeException' nad objektom klase tipa Osoba da li objekti imaju iste odabrane osobe dva puta gdje se onda hvata i obrađuje nova oznadena iznimka, prima objekt klase tipa Scanner, listu objekata klase tipa Osoba i objekt klase tipa Integer, a ne vraća ništa.
     *
     * @param scan              Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param osobe             List Osoba set objekata služi za provjeru duplikata nad kontaktirnom osobom za svaku osobu koja se unosi.
     * @param kontaktiraneOsobe List Osoba set objekata služi za provjeru duplikata svake kontaktirane osobe koja je bila sa više istih osoba u kontaktu.
     */
    protected static void hvatanjeOznaceneIznimkeDuplikataOsobe(Scanner scan, List<Osoba> osobe, List<Osoba> kontaktiraneOsobe) {

        boolean vrtiPetlju = false;
        do {
            vrtiPetlju = false;
            try {
                Osoba kontaktirana = MetodeZaOdabir.odabirOsobe(scan, osobe);
                provjeraDuplikataKontaktiranihOsoba(kontaktirana, kontaktiraneOsobe);
                kontaktiraneOsobe.add(kontaktirana);
            } catch (DuplikatKontaktiraneOsobeException ex) {
                logger.error("Pogreska, dogodila se nova oznacena iznimka, odabrana je ista osoba vise puta!", ex);
                System.err.println("Odabrana je ista osoba vise puta" + messageRepeatInput + "!\n");
                vrtiPetlju = true;
            }
        }
        while (vrtiPetlju);
    }

    /**
     * Služi za hvatanje neoznačene iznimke 'BolestiIstihSimptomaException' nad poljem objekata klase tipa Simptom i Bolest da li objekti imaju iste odabrane simptome više puta, gdje se onda hvata i obrađuje nova neoznačena iznimka, prima objekt klase tipa Scanner, set objekata klase tipa Simtom i Bolest, objekt klase tipa Integer, a ne vraća ništa.
     *
     * @param scan     Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param simptomi Set Simptom set objekata služi za provjeru istih simptoma.
     * @param bolesti  Set Bolest set objekata služi za provjeru istih simptoma za naziv druge bolesti koja se unosi.
     * @param i        Integer indeks elementa od objekta bolesti.
     */
    protected static void hvatanjeNeoznaceneIznimkeBolestIstihSimptoma(Scanner scan, SviImenovaniEntiteti<ImenovaniEntitet> nazivEntiteta, Set<Simptom> simptomi, Set<Bolest> bolesti, KlinikaZaInfektivneBolesti<Virus, Osoba> klinikaZaInfektivneBolesti, Integer i) {

        boolean vrtiPetlju = false;
        do {
            vrtiPetlju = false;
            try {

                Bolest bolest = MetodeZaUnosPodataka.unosBolesti(scan, simptomi, i);
                MetodeZaValidaciju.provjeraBolestiSaIstimSimptomima(bolest, bolesti);
                nazivEntiteta.dodajImenovaniEntitet(bolest);
                bolesti.add(bolest);
                if (bolest instanceof Virus virus) {
                    klinikaZaInfektivneBolesti.dodajBolestiZarazeneVirusom(virus);
                }

            } catch (BolestIstihSimptomaException ex) {
                logger.error("Pogreska, dogodila se nova neoznacena iznimka, odabrana je bolest sa istim simptomima vise puta!", ex);
                System.err.print("Odabrana je bolest sa istim simptomima vise puta" + messageRepeatInput + "!\n");
                vrtiPetlju = true;
            }
        }
        while (vrtiPetlju);
    }

    /**
     * Kod unosa cjelobrojnih vrijednosti provjerava da li je unesena vrijednost unutar granice svakog od veličine polja, da li je broj negativan ili jednak nuli, prima objekt klase tipa Scanner i Integer, a vraca objekt klase tipa Integer.
     *
     * @param scan    Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param granica Integer predstavlja konstantu veličinu polja.
     * @return Integer odabrani indeks odnosno cjelobrojna vrijednost.
     */
    protected static Integer provjeraBrojaSaListe(Scanner scan, Integer granica) {

        int odabir;
        do {
            odabir = MetodeZaValidaciju.IntegerExHandler(scan);
            if (odabir < 0 || odabir > granica) {
                System.out.print("Odabir mora biti redni broj sa ponudene liste" + messageRepeatInput);
                logger.error(messageRepeatNumberFormatEx + " '" + odabir + "'");
            } else if (odabir == 0)
                System.out.print("Odabir ne moze biti nula" + messageRepeatInput);
            logger.error(messageRepeatNumberFormatEx + " '" + odabir + "'");
        } while (odabir < 0 || odabir > granica || odabir == 0);
        return odabir;
    }

    /**
     * Kod unosa cjelobrojnih vrijednosti provjerava da li je broj kontaktiranih osoba manji ili jednak broju koji je poslan sa indeksom, prima objekt klase tipa Scanner i Integer, a vraca objekt klase tipa Integer.
     *
     * @param scan        scan – Scanner objekt za slanje podataka u racunalo preko tipkovnice.
     * @param brojacOsoba Integer predstavlja prosljeđeni broj osoba koji je uvjek za jedan manji od ukupno unesenih.
     * @return Integer vraca broj kontaktiranih odnosno cjelobrojnu vrijednost.
     */
    protected static Integer provjeraBrojaKontaktiranih(Scanner scan, Integer brojacOsoba) {

        Integer brojKontaktiranih;
        do {
            brojKontaktiranih = MetodeZaValidaciju.IntegerExHandler(scan);
            if (brojKontaktiranih <= brojacOsoba) {
                return brojKontaktiranih;
            } else if (brojKontaktiranih == 0) {
                logger.error(messageRepeatNumberFormatEx + " '" + brojKontaktiranih + "'");
                System.err.print("Nema kontaktiranih osoba" + messageRepeatInput);
            } else {
                logger.error("Broj kontaktiranih osoba ne moze biti veci od broja upisanih osoba " + " '" + brojKontaktiranih + "' ");
                System.err.print("Broj kontaktiranih osoba ne moze biti veci od broja upisanih osoba" + messageRepeatInput);
            }
        } while (true);
    }

    /**
     * Brojac osoba koji služi kod odabira osoba da pošalje točan broj osoba koliko ih mora biti, prima polje objekata klase tipa Osoba, a vraca objekt klase tipa Integer.
     *
     * @param osobe Lista Osobe lista objekata služi za prebrojavanje unesenih kontaktiranih osoba.
     * @return Integer broj točan kontaktiranih osoba.
     */
    protected static Integer brojKontaktiranihOsoba(List<Osoba> osobe) {

        Integer brojac = 0;
        for (Osoba osoba : osobe) {
            if (Optional.ofNullable(osoba).isPresent()) {
                brojac++;
            }
        }
        return brojac;
    }
}